package com.dashcam.ai.auth

import android.content.Context

data class AuthSession(
    val userId: String,
    val email: String,
    val token: String,
    val loggedIn: Boolean
)

class AuthSessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun snapshot(): AuthSession {
        val token = prefs.getString(KEY_TOKEN, "").orEmpty()
        return AuthSession(
            userId = prefs.getString(KEY_USER_ID, "").orEmpty(),
            email = prefs.getString(KEY_EMAIL, "").orEmpty(),
            token = token,
            loggedIn = token.isNotBlank()
        )
    }

    fun save(userId: String, email: String, token: String) {
        prefs.edit()
            .putString(KEY_USER_ID, userId.trim())
            .putString(KEY_EMAIL, email.trim())
            .putString(KEY_TOKEN, token.trim())
            .apply()
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "auth_session"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_EMAIL = "email"
        private const val KEY_TOKEN = "token"
    }
}
