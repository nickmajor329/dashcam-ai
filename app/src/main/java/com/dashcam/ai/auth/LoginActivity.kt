package com.dashcam.ai.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dashcam.ai.databinding.ActivityLoginBinding
import com.dashcam.ai.network.BackendApiClient
import com.dashcam.ai.pairing.PairingManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authSessionManager: AuthSessionManager
    private lateinit var pairingManager: PairingManager
    private val apiClient = BackendApiClient()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authSessionManager = AuthSessionManager(this)
        pairingManager = PairingManager(this)
        val current = authSessionManager.snapshot()
        if (current.loggedIn) {
            binding.emailInput.setText(current.email)
            binding.statusText.text = "Signed in as ${current.email}"
        }

        binding.backButton.setOnClickListener { finish() }
        binding.loginButton.setOnClickListener { submitAuth(isRegister = false) }
        binding.registerButton.setOnClickListener { submitAuth(isRegister = true) }
        binding.logoutButton.setOnClickListener {
            authSessionManager.clear()
            pairingManager.clearOwner()
            Toast.makeText(this, "Signed out", Toast.LENGTH_SHORT).show()
            binding.statusText.text = "Signed out"
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun submitAuth(isRegister: Boolean) {
        val email = binding.emailInput.text?.toString().orEmpty().trim()
        val password = binding.passwordInput.text?.toString().orEmpty()
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and password are required", Toast.LENGTH_SHORT).show()
            return
        }

        scope.launch {
            binding.statusText.text = if (isRegister) "Creating account..." else "Signing in..."
            val path = if (isRegister) "/api/v1/auth/register" else "/api/v1/auth/login"
            val payload = JSONObject()
                .put("email", email)
                .put("password", password)
            val response = withContext(Dispatchers.IO) {
                apiClient.postJson(path, payload)
            }
            if (!response.success) {
                binding.statusText.text = "Auth failed (${response.code})"
                return@launch
            }

            val body = runCatching { JSONObject(response.body) }.getOrNull()
            val token = body?.optString("token").orEmpty()
            val user = body?.optJSONObject("user")
            val userId = user?.optString("id").orEmpty()
            val responseEmail = user?.optString("email").orEmpty().ifBlank { email }
            if (token.isBlank() || userId.isBlank()) {
                binding.statusText.text = "Missing auth response fields"
                return@launch
            }

            authSessionManager.save(userId, responseEmail, token)
            pairingManager.clearOwner()
            pairingManager.setOwnerId(userId)
            binding.statusText.text = "Signed in as $responseEmail"
            Toast.makeText(this@LoginActivity, "Signed in", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
