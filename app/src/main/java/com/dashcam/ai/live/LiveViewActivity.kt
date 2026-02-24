package com.dashcam.ai.live

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dashcam.ai.auth.AuthSessionManager
import com.dashcam.ai.databinding.ActivityLiveViewBinding
import com.dashcam.ai.network.BackendApiClient
import com.dashcam.ai.pairing.PairingManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LiveViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveViewBinding
    private lateinit var pairingManager: PairingManager
    private lateinit var authSessionManager: AuthSessionManager
    private val apiClient = BackendApiClient()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onDestroy() {
        runCatching { binding.liveWebView.destroy() }
        scope.coroutineContext.cancel()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pairingManager = PairingManager(this)
        authSessionManager = AuthSessionManager(this)

        binding.liveWebView.settings.javaScriptEnabled = true
        binding.liveWebView.settings.mediaPlaybackRequiresUserGesture = false
        binding.liveWebView.settings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
        binding.liveWebView.webViewClient = WebViewClient()
        binding.liveWebView.webChromeClient = WebChromeClient()

        binding.backButton.setOnClickListener { finish() }
        binding.openViewerButton.setOnClickListener { requestAndOpenLiveUrl("viewer") }
        binding.openPublisherButton.setOnClickListener { requestAndOpenLiveUrl("publisher") }
        binding.openNativeViewerButton.setOnClickListener { requestAndOpenNativeLiveViewer() }
        binding.snapshotButton.setOnClickListener { saveSnapshot() }
    }

    private fun requestAndOpenLiveUrl(role: String) {
        val state = pairingManager.snapshot()
        if (!state.paired && role == "viewer") {
            Toast.makeText(this, "Pair device first", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            binding.liveStatusText.text = "Requesting $role session..."
            val payload = JSONObject()
                .put("role", role)
                .put("vehicle_id", state.vehicleId)
                .put("owner_id", state.pairedOwnerId)
                .put("bitrate_kbps", selectedBitrateKbps())

            val result = withContext(Dispatchers.IO) {
                apiClient.postJson("/api/v1/live/session/url", payload, authSessionManager.snapshot().token)
            }

            if (!result.success) {
                binding.liveStatusText.text = "Failed: ${result.body.ifBlank { "HTTP ${result.code}" }}"
                return@launch
            }

            val liveUrl = runCatching { JSONObject(result.body).optString("live_url") }.getOrDefault("")
            if (liveUrl.isBlank()) {
                binding.liveStatusText.text = "No live URL from server"
                return@launch
            }

            binding.liveStatusText.text = "Loading live view"
            binding.liveWebView.loadUrl(liveUrl)
        }
    }

    private fun requestAndOpenNativeLiveViewer() {
        val state = pairingManager.snapshot()
        if (!state.paired) {
            Toast.makeText(this, "Pair device first", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            binding.liveStatusText.text = "Requesting native WebRTC session..."
            val payload = JSONObject()
                .put("role", "viewer")
                .put("vehicle_id", state.vehicleId)
                .put("owner_id", state.pairedOwnerId)
                .put("bitrate_kbps", selectedBitrateKbps())

            val result = withContext(Dispatchers.IO) {
                apiClient.postJson("/api/v1/live/session/native", payload, authSessionManager.snapshot().token)
            }
            if (!result.success) {
                binding.liveStatusText.text = "Native session request failed"
                return@launch
            }

            val body = runCatching { JSONObject(result.body) }.getOrNull()
            val supported = body?.optBoolean("supported", false) == true
            val fallbackUrl = body?.optString("fallback_live_url").orEmpty()

            if (!supported) {
                binding.liveStatusText.text =
                    body?.optString("reason").takeUnless { it.isNullOrBlank() } ?: "Native WebRTC unavailable, using web fallback"
                if (fallbackUrl.isNotBlank()) {
                    binding.liveWebView.loadUrl(fallbackUrl)
                }
                return@launch
            }

            val wsUrl = body?.optString("ws_url").orEmpty()
            val accessToken = body?.optString("access_token").orEmpty()
            if (wsUrl.isBlank() || accessToken.isBlank()) {
                binding.liveStatusText.text = "Native session missing token details"
                return@launch
            }

            val liveKitViewerUrl = Uri.Builder()
                .scheme("https")
                .authority("meet.livekit.io")
                .appendPath("custom")
                .appendQueryParameter("liveKitUrl", wsUrl)
                .appendQueryParameter("token", accessToken)
                .build()
                .toString()

            binding.liveStatusText.text = "Opening low-latency live session"
            binding.liveWebView.loadUrl(liveKitViewerUrl)
        }
    }

    private fun selectedBitrateKbps(): Int {
        return when (binding.bitrateGroup.checkedRadioButtonId) {
            binding.bitrateLow.id -> 500
            binding.bitrateHigh.id -> 2500
            else -> 1200
        }
    }

    private fun saveSnapshot() {
        val bitmap = captureWebViewBitmap() ?: run {
            Toast.makeText(this, "Unable to capture snapshot", Toast.LENGTH_SHORT).show()
            return
        }

        val saved = runCatching { saveBitmapToGallery(bitmap) }.getOrNull()
        if (saved.isNullOrBlank()) {
            Toast.makeText(this, "Snapshot failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Snapshot saved: $saved", Toast.LENGTH_LONG).show()
        }
    }

    private fun captureWebViewBitmap(): Bitmap? {
        val view = binding.liveWebView
        if (view.width <= 0 || view.height <= 0) return null
        return runCatching {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            view.draw(canvas)
            bitmap
        }.getOrNull()
    }

    private fun saveBitmapToGallery(bitmap: Bitmap): String {
        val fileName = "dashcam_snapshot_${System.currentTimeMillis()}.jpg"
        val resolver = contentResolver
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/DashcamAI")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: error("Unable to create snapshot media entry")

        resolver.openOutputStream(uri)?.use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 92, out)
        } ?: error("Unable to open snapshot output stream")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
        }

        return fileName
    }
}
