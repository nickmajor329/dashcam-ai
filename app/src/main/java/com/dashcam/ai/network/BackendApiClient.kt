package com.dashcam.ai.network

import com.dashcam.ai.BuildConfig
import org.json.JSONObject
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class BackendApiClient {

    data class ApiResult(
        val success: Boolean,
        val code: Int,
        val body: String
    )

    fun postJson(path: String, payload: JSONObject, userToken: String? = null): ApiResult {
        val baseUrl = BuildConfig.ALERT_API_BASE_URL.trim().trimEnd('/')
        if (baseUrl.isBlank()) return ApiResult(false, -1, "ALERT_API_BASE_URL not configured")

        val connection = (URL(baseUrl + path).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 15_000
            readTimeout = 30_000
            doInput = true
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("Accept", "application/json")
            val apiKey = BuildConfig.ALERT_API_KEY.trim()
            if (apiKey.isNotBlank()) {
                setRequestProperty("X-Api-Key", apiKey)
            }
            val token = userToken?.trim().orEmpty()
            if (token.isNotBlank()) {
                setRequestProperty("Authorization", "Bearer $token")
            } else if (apiKey.isNotBlank()) {
                setRequestProperty("Authorization", "Bearer $apiKey")
            }
        }

        return runCatching {
            OutputStreamWriter(connection.outputStream).use {
                it.write(payload.toString())
                it.flush()
            }
            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val body = stream?.bufferedReader()?.use(BufferedReader::readText).orEmpty()
            ApiResult(code in 200..299, code, body)
        }.getOrElse {
            ApiResult(false, -1, it.message.orEmpty())
        }.also {
            connection.disconnect()
        }
    }
}
