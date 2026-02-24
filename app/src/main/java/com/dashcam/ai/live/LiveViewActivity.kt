package com.dashcam.ai.live

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.WindowManager
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dashcam.ai.auth.AuthSessionManager
import com.dashcam.ai.BuildConfig
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
    private val mediaPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val hasCamera = result[Manifest.permission.CAMERA] == true ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val hasAudio = result[Manifest.permission.RECORD_AUDIO] == true ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (hasCamera && hasAudio) {
            requestAndOpenNativeLivePublisher()
        } else {
            Toast.makeText(this, "Camera/mic permission required for live publish", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        runCatching { binding.liveWebView.destroy() }
        scope.coroutineContext.cancel()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = ActivityLiveViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pairingManager = PairingManager(this)
        authSessionManager = AuthSessionManager(this)

        binding.liveWebView.settings.javaScriptEnabled = true
        binding.liveWebView.settings.mediaPlaybackRequiresUserGesture = false
        binding.liveWebView.settings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
        binding.liveWebView.webViewClient = WebViewClient()
        binding.liveWebView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                if (request == null) return
                val hasCamera = ContextCompat.checkSelfPermission(
                    this@LiveViewActivity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
                val hasAudio = ContextCompat.checkSelfPermission(
                    this@LiveViewActivity,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
                if (hasCamera && hasAudio) {
                    request.grant(request.resources)
                } else {
                    request.deny()
                    runOnUiThread {
                        Toast.makeText(
                            this@LiveViewActivity,
                            "Camera/mic permission required for publisher stream",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.backButton.setOnClickListener { finish() }
        binding.openViewerButton.setOnClickListener { requestAndOpenLiveUrl("viewer") }
        binding.openPublisherButton.setOnClickListener {
            if (hasMediaPermissions()) {
                requestAndOpenNativeLivePublisher()
            } else {
                mediaPermissionLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                )
            }
        }
        binding.openNativeViewerButton.setOnClickListener { requestAndOpenNativeLiveViewer() }
        binding.snapshotButton.setOnClickListener { saveSnapshot() }
    }

    private fun requestAndOpenLiveUrl(role: String) {
        val state = pairingManager.snapshot()
        val session = authSessionManager.snapshot()
        val vehicleId = state.activeVehicleId.ifBlank { state.vehicleId }
        val ownerId = state.pairedOwnerId.ifBlank { session.userId }
        if (vehicleId.isBlank()) {
            Toast.makeText(this, "Set vehicle ID in Pair screen first", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            binding.liveStatusText.text = "Requesting $role session..."
            val payload = JSONObject()
                .put("role", role)
                .put("vehicle_id", vehicleId)
                .put("owner_id", ownerId)
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
        val session = authSessionManager.snapshot()
        val vehicleId = state.activeVehicleId.ifBlank { state.vehicleId }
        val ownerId = state.pairedOwnerId.ifBlank { session.userId }
        if (vehicleId.isBlank()) {
            Toast.makeText(this, "Set vehicle ID in Pair screen first", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            binding.liveStatusText.text = "Requesting native WebRTC session..."
            val payload = JSONObject()
                .put("role", "viewer")
                .put("vehicle_id", vehicleId)
                .put("owner_id", ownerId)
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

    private fun requestAndOpenNativeLivePublisher() {
        val state = pairingManager.snapshot()
        val session = authSessionManager.snapshot()
        val vehicleId = state.activeVehicleId.ifBlank { state.vehicleId }
        val ownerId = state.pairedOwnerId.ifBlank { session.userId }
        if (vehicleId.isBlank()) {
            Toast.makeText(this, "Set vehicle ID in Pair screen first", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            binding.liveStatusText.text = "Requesting native publisher session..."
            val payload = JSONObject()
                .put("role", "publisher")
                .put("vehicle_id", vehicleId)
                .put("owner_id", ownerId)
                .put("bitrate_kbps", selectedBitrateKbps())

            val result = withContext(Dispatchers.IO) {
                apiClient.postJson("/api/v1/live/session/native", payload, authSessionManager.snapshot().token)
            }
            if (!result.success) {
                binding.liveStatusText.text = "Publisher session request failed"
                return@launch
            }

            val body = runCatching { JSONObject(result.body) }.getOrNull()
            val supported = body?.optBoolean("supported", false) == true
            if (!supported) {
                val reason = body?.optString("reason").takeUnless { it.isNullOrBlank() }
                    ?: "Native publish unavailable"
                binding.liveStatusText.text = reason
                val fallbackUrl = body?.optString("fallback_live_url").orEmpty()
                if (fallbackUrl.isNotBlank()) {
                    binding.liveWebView.loadUrl(fallbackUrl)
                }
                return@launch
            }

            val wsUrl = body?.optString("ws_url").orEmpty()
            val accessToken = body?.optString("access_token").orEmpty()
            if (wsUrl.isBlank() || accessToken.isBlank()) {
                binding.liveStatusText.text = "Publisher session missing token details"
                return@launch
            }

            val liveKitPublisherUrl = Uri.Builder()
                .encodedPath("/live/auto-publisher")
                .appendQueryParameter("ws_url", wsUrl)
                .appendQueryParameter("token", accessToken)
                .build()
                .let { appendBackendBase(it) }

            binding.liveStatusText.text = "Opening publisher in browser (keep it open)"
            startActivity(
                Intent(Intent.ACTION_VIEW, liveKitPublisherUrl)
            )
        }
    }

    private fun hasMediaPermissions(): Boolean {
        val hasCamera = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val hasAudio = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        return hasCamera && hasAudio
    }

    private fun appendBackendBase(relative: Uri): Uri {
        val base = BuildConfig.ALERT_API_BASE_URL.trim().trimEnd('/')
        return Uri.parse("$base${relative.encodedPath}?${relative.encodedQuery}")
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
