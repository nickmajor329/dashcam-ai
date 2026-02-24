package com.dashcam.ai.pairing

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.dashcam.ai.auth.AuthSessionManager
import com.dashcam.ai.databinding.ActivityPairingBinding
import com.dashcam.ai.network.BackendApiClient
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class PairingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPairingBinding
    private lateinit var pairingManager: PairingManager
    private lateinit var authSessionManager: AuthSessionManager
    private val apiClient = BackendApiClient()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val scanLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }

        val payload = result.data?.getStringExtra(QrScannerActivity.EXTRA_QR_CONTENT).orEmpty()
        if (payload.isBlank()) {
            Toast.makeText(this, "No QR content detected", Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }
        completePairing(payload)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPairingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pairingManager = PairingManager(this)
        authSessionManager = AuthSessionManager(this)

        val state = pairingManager.snapshot()
        val session = authSessionManager.snapshot()
        if (session.loggedIn && session.userId.isNotBlank()) {
            pairingManager.setOwnerId(session.userId)
        }
        binding.vehicleIdInput.setText(state.activeVehicleId.ifBlank { state.vehicleId })
        binding.roleInput.setText(state.activeRole)
        updateStateText()

        binding.backButton.setOnClickListener { finish() }
        binding.generateQrButton.setOnClickListener { generatePairQr() }
        binding.scanPairQrButton.setOnClickListener { scanPairQr() }
        binding.setActiveVehicleButton.setOnClickListener {
            val vehicleId = binding.vehicleIdInput.text?.toString().orEmpty().trim()
            if (vehicleId.isBlank()) {
                Toast.makeText(this, "Enter vehicle ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            pairingManager.setActiveVehicle(vehicleId)
            updateStateText()
            Toast.makeText(this, "Active vehicle set", Toast.LENGTH_SHORT).show()
        }
        binding.clearPairingButton.setOnClickListener {
            pairingManager.clearPairing()
            updateStateText()
            Toast.makeText(this, "Pairing cleared", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePairQr() {
        val vehicleId = binding.vehicleIdInput.text?.toString().orEmpty().trim()
        if (vehicleId.isBlank()) {
            Toast.makeText(this, "Enter vehicle ID first", Toast.LENGTH_SHORT).show()
            return
        }
        pairingManager.setVehicleId(vehicleId)

        scope.launch {
            binding.statusText.text = "Requesting pairing token..."
            val payload = JSONObject().put("vehicle_id", vehicleId)
            val result = withContext(Dispatchers.IO) {
                apiClient.postJson("/api/v1/pairing/session/start", payload, authSessionManager.snapshot().token)
            }
            if (!result.success) {
                binding.statusText.text = "Failed: ${result.body.ifBlank { "HTTP ${result.code}" }}"
                return@launch
            }

            val body = runCatching { JSONObject(result.body) }.getOrNull()
            val pairingToken = body?.optString("pairing_token").orEmpty()
            if (pairingToken.isBlank()) {
                binding.statusText.text = "No pairing token returned"
                return@launch
            }

            val qrPayload = JSONObject()
                .put("type", "dashcam_pair")
                .put("pairing_token", pairingToken)
                .toString()

            val bitmap = createQrBitmap(qrPayload)
            binding.qrImage.setImageBitmap(bitmap)
            binding.qrImage.isVisible = true
            binding.statusText.text = "Scan this QR from owner phone"
        }
    }

    private fun scanPairQr() {
        scanLauncher.launch(android.content.Intent(this, QrScannerActivity::class.java))
    }

    private fun completePairing(qrPayload: String) {
        scope.launch {
            val session = authSessionManager.snapshot()
            if (!session.loggedIn) {
                binding.statusText.text = "Sign in first (Account button on main screen)"
                Toast.makeText(this@PairingActivity, "Sign in first", Toast.LENGTH_SHORT).show()
                return@launch
            }
            val token = runCatching {
                JSONObject(qrPayload).optString("pairing_token")
            }.getOrDefault("")
            if (token.isBlank()) {
                binding.statusText.text = "Invalid QR payload"
                return@launch
            }

            binding.statusText.text = "Completing pairing..."
            val state = pairingManager.snapshot()
            val requestedRole = normalizeRole(binding.roleInput.text?.toString().orEmpty())
            val effectiveOwnerId = session.userId.ifBlank { state.pairedOwnerId }
            val payload = JSONObject().put("pairing_token", token)
                .put("owner_id", effectiveOwnerId)
                .put("role", requestedRole)
            val result = withContext(Dispatchers.IO) {
                apiClient.postJson("/api/v1/pairing/session/complete", payload, session.token)
            }
            if (!result.success) {
                binding.statusText.text = "Pair failed: ${result.body.ifBlank { "HTTP ${result.code}" }}"
                return@launch
            }

            val body = runCatching { JSONObject(result.body) }.getOrNull()
            val ownerId = body?.optString("owner_id").orEmpty().ifBlank { "owner" }
            val vehicleId = body?.optString("vehicle_id").orEmpty()
            val role = normalizeRole(body?.optString("role").orEmpty())
            pairingManager.markPaired(ownerId)
            if (vehicleId.isNotBlank()) {
                pairingManager.addOrUpdateFleetVehicle(vehicleId, role)
            }
            updateStateText()
            binding.statusText.text = "Pairing complete"
        }
    }

    private fun updateStateText() {
        val state = pairingManager.snapshot()
        val msg = if (state.paired) {
            "Owner: ${state.pairedOwnerId} | Active: ${state.activeVehicleId.ifBlank { state.vehicleId }} (${state.activeRole})"
        } else {
            "Not paired"
        }
        binding.pairStateText.text = msg
        binding.fleetText.text = if (state.fleet.isEmpty()) {
            "Fleet: none"
        } else {
            "Fleet: " + state.fleet.joinToString(" | ") { "${it.vehicleId}:${it.role}" }
        }
    }

    private fun normalizeRole(raw: String): String {
        return when (raw.trim().uppercase()) {
            "OWNER", "ADMIN", "DRIVER", "VIEWER" -> raw.trim().uppercase()
            else -> "OWNER"
        }
    }

    private fun createQrBitmap(content: String): android.graphics.Bitmap {
        val matrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, 720, 720)
        val width = matrix.width
        val height = matrix.height
        val bitmap = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (matrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bitmap
    }
}
