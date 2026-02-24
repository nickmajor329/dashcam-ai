package com.dashcam.ai.alerts

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.dashcam.ai.BuildConfig
import com.dashcam.ai.auth.AuthSessionManager
import java.io.BufferedOutputStream
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID

class AlertApiClient(private val context: Context) {

    private val authSessionManager = AuthSessionManager(context)

    fun uploadEvent(
        eventId: Long,
        eventType: String,
        clipPath: String,
        createdAtMs: Long,
        ownerId: String,
        vehicleId: String,
        appEnabled: Boolean,
        emailEnabled: Boolean,
        smsEnabled: Boolean,
        appDeviceId: String,
        emailAddress: String,
        phoneNumber: String,
        blurFaces: Boolean,
        blurPlates: Boolean
    ): Boolean {
        val baseUrl = BuildConfig.ALERT_API_BASE_URL.trim().trimEnd('/')
        if (baseUrl.isBlank()) {
            Log.w(TAG, "ALERT_API_BASE_URL not configured")
            return false
        }

        val clipFile = File(clipPath)
        if (!clipFile.exists()) {
            Log.e(TAG, "Clip file missing: $clipPath")
            return false
        }

        val boundary = "----DashcamBoundary${UUID.randomUUID()}"
        val endpoint = URL("$baseUrl/api/v1/dashcam/events")
        val connection = (endpoint.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 30_000
            doInput = true
            doOutput = true
            setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
            setRequestProperty("Accept", "application/json")
            val apiKey = BuildConfig.ALERT_API_KEY.trim()
            if (apiKey.isNotBlank()) {
                setRequestProperty("X-Api-Key", apiKey)
            }
            val userToken = authSessionManager.snapshot().token
            if (userToken.isNotBlank()) {
                setRequestProperty("Authorization", "Bearer $userToken")
            } else if (apiKey.isNotBlank()) {
                setRequestProperty("Authorization", "Bearer $apiKey")
            }
        }

        return runCatching {
            BufferedOutputStream(connection.outputStream).use { stream ->
                writeFormField(stream, boundary, "event_id", eventId.toString())
                writeFormField(stream, boundary, "event_type", eventType)
                writeFormField(stream, boundary, "created_at_ms", createdAtMs.toString())
                writeFormField(stream, boundary, "source_device", sourceDeviceId())
                writeFormField(stream, boundary, "owner_id", ownerId)
                writeFormField(stream, boundary, "vehicle_id", vehicleId)
                writeFormField(stream, boundary, "route_app_enabled", appEnabled.toString())
                writeFormField(stream, boundary, "route_email_enabled", emailEnabled.toString())
                writeFormField(stream, boundary, "route_sms_enabled", smsEnabled.toString())
                writeFormField(stream, boundary, "target_app_device_id", appDeviceId)
                writeFormField(stream, boundary, "target_email", emailAddress)
                writeFormField(stream, boundary, "target_phone", phoneNumber)
                writeFormField(stream, boundary, "privacy_blur_faces", blurFaces.toString())
                writeFormField(stream, boundary, "privacy_blur_plates", blurPlates.toString())
                writeFileField(stream, boundary, "clip", clipFile)
                stream.write("--$boundary--\r\n".toByteArray())
                stream.flush()
            }

            val code = connection.responseCode
            code in 200..299
        }.onFailure {
            Log.e(TAG, "Upload failed", it)
        }.getOrDefault(false).also {
            connection.disconnect()
        }
    }

    private fun sourceDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID).orEmpty()
    }

    private fun writeFormField(
        stream: BufferedOutputStream,
        boundary: String,
        name: String,
        value: String
    ) {
        stream.write("--$boundary\r\n".toByteArray())
        stream.write("Content-Disposition: form-data; name=\"$name\"\r\n\r\n".toByteArray())
        stream.write(value.toByteArray())
        stream.write("\r\n".toByteArray())
    }

    private fun writeFileField(
        stream: BufferedOutputStream,
        boundary: String,
        name: String,
        file: File
    ) {
        stream.write("--$boundary\r\n".toByteArray())
        stream.write(
            "Content-Disposition: form-data; name=\"$name\"; filename=\"${file.name}\"\r\n".toByteArray()
        )
        stream.write("Content-Type: video/mp4\r\n\r\n".toByteArray())
        file.inputStream().use { input -> input.copyTo(stream) }
        stream.write("\r\n".toByteArray())
    }

    companion object {
        private const val TAG = "AlertApiClient"
    }
}
