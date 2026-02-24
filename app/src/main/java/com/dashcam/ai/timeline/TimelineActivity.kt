package com.dashcam.ai.timeline

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dashcam.ai.BuildConfig
import com.dashcam.ai.data.EventEntity
import com.dashcam.ai.data.EventRepository
import com.dashcam.ai.data.EventType
import com.dashcam.ai.databinding.ActivityTimelineBinding
import com.dashcam.ai.location.ParkedStateManager
import com.dashcam.ai.pairing.PairingManager
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.MessageDigest
import java.security.SecureRandom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class TimelineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTimelineBinding
    private lateinit var repository: EventRepository
    private lateinit var adapter: EventTimelineAdapter
    private lateinit var parkedStateManager: ParkedStateManager
    private lateinit var pairingManager: PairingManager

    private var selectedType: EventType? = null
    private var pendingOnly: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimelineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = EventRepository(this)
        parkedStateManager = ParkedStateManager(this)
        pairingManager = PairingManager(this)
        adapter = EventTimelineAdapter(
            onShare = { shareClip(it) },
            onExport = { exportEvidencePackage(it) }
        )

        binding.backButton.setOnClickListener { finish() }
        binding.timelineList.layoutManager = LinearLayoutManager(this)
        binding.timelineList.adapter = adapter

        setupFilters()
        loadTimeline()
    }

    private fun setupFilters() {
        binding.pendingOnlySwitch.setOnCheckedChangeListener { _, checked ->
            pendingOnly = checked
            loadTimeline()
        }

        binding.filterChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedType = when (checkedIds.firstOrNull()) {
                binding.filterMotion.id -> EventType.MOTION_NEAR_VEHICLE
                binding.filterPerson.id -> EventType.PERSON_NEAR_WINDOW
                binding.filterImpact.id -> EventType.IMPACT
                else -> null
            }
            loadTimeline()
        }
    }

    private fun loadTimeline() {
        lifecycleScope.launch {
            val events = withContext(Dispatchers.IO) {
                repository.timeline(selectedType, pendingOnly)
            }
            adapter.submit(events)
            binding.emptyText.text = if (events.isEmpty()) {
                "No events for current filter"
            } else {
                "${events.size} events"
            }
        }
    }

    private fun shareClip(event: EventEntity) {
        val clip = File(event.clipPath)
        if (!clip.exists()) {
            Toast.makeText(this, "Clip file not found", Toast.LENGTH_SHORT).show()
            return
        }

        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", clip)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "video/mp4"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Dashcam event ${event.eventType}")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(intent, "Share clip"))
    }

    private fun exportEvidencePackage(event: EventEntity) {
        val clip = File(event.clipPath)
        if (!clip.exists()) {
            Toast.makeText(this, "Clip file not found", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching {
                    createEvidenceBundle(event, clip)
                }
            }

            result.onSuccess {
                Toast.makeText(this@TimelineActivity, "Evidence bundle exported", Toast.LENGTH_SHORT).show()
                shareEvidenceBundle(it.shareFile, it.exportName)
            }.onFailure {
                Toast.makeText(this@TimelineActivity, "Evidence export failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareEvidenceBundle(bundleFile: File, exportName: String) {
        val uri = FileProvider.getUriForFile(this, "$packageName.fileprovider", bundleFile)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/zip"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Dashcam evidence package $exportName")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(intent, "Share evidence package"))
    }

    private fun createEvidenceBundle(event: EventEntity, clip: File): EvidenceExportResult {
        val evidenceRoot = File(filesDir, "evidence_packages").apply { mkdirs() }
        val packageBase = "evidence_${event.id}_${event.createdAtMs}"
        val shareZip = File(evidenceRoot, "$packageBase.zip")
        val workDir = File(evidenceRoot, "${packageBase}_work").apply {
            if (exists()) deleteRecursively()
            mkdirs()
        }

        val clipName = if (clip.extension.isBlank()) "clip.mp4" else "clip.${clip.extension}"
        val clipCopy = File(workDir, clipName)
        clip.inputStream().use { input -> clipCopy.outputStream().use { out -> input.copyTo(out) } }

        val metadataFile = File(workDir, "metadata.json")
        metadataFile.writeText(buildMetadataJson(event, clip, clipCopy).toString(2))

        val gpsFile = File(workDir, "gps_path.geojson")
        gpsFile.writeText(buildGpsGeoJson(event.createdAtMs).toString(2))

        val hashLogFile = File(workDir, "hashes.log")
        val hashLines = listOf(
            "SHA256 ${clipCopy.name} ${sha256Hex(clipCopy)}",
            "SHA256 ${metadataFile.name} ${sha256Hex(metadataFile)}",
            "SHA256 ${gpsFile.name} ${sha256Hex(gpsFile)}"
        )
        hashLogFile.writeText(hashLines.joinToString("\n", postfix = "\n"))

        val signatureFile = File(workDir, "signature.log")
        val signature = hmacSha256Base64(hashLogFile.readText())
        signatureFile.writeText(
            buildString {
                appendLine("ALGO HMAC-SHA256")
                appendLine("KEY_ID ${evidenceKeyId()}")
                appendLine("SIGNATURE $signature")
            }
        )

        zipFiles(
            files = listOf(clipCopy, metadataFile, gpsFile, hashLogFile, signatureFile),
            target = shareZip
        )
        workDir.deleteRecursively()

        val exportName = exportZipForUser(shareZip)
        return EvidenceExportResult(shareFile = shareZip, exportName = exportName)
    }

    private fun exportZipForUser(source: File): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            exportZipToMediaStore(source)
        } else {
            exportZipToAppExternal(source)
        }
    }

    private fun exportZipToMediaStore(source: File): String {
        val resolver = contentResolver
        val fileName = source.name

        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/zip")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_DOWNLOADS}/DashcamAI")
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            ?: error("Unable to create export entry")

        try {
            resolver.openOutputStream(uri)?.use { out ->
                source.inputStream().use { input ->
                    input.copyTo(out)
                }
            } ?: error("Unable to open export output stream")

            values.clear()
            values.put(MediaStore.MediaColumns.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
            return fileName
        } catch (t: Throwable) {
            resolver.delete(uri, null, null)
            throw t
        }
    }

    private fun exportZipToAppExternal(source: File): String {
        val directory = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "DashcamAI")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val target = File(directory, source.name)

        source.inputStream().use { input ->
            target.outputStream().use { out ->
                input.copyTo(out)
            }
        }

        return target.absolutePath
    }

    private fun buildMetadataJson(event: EventEntity, originalClip: File, packagedClip: File): JSONObject {
        val pairing = pairingManager.snapshot()
        val now = System.currentTimeMillis()
        val appVersion = runCatching {
            packageManager.getPackageInfo(packageName, 0).versionName
        }.getOrElse {
            BuildConfig.VERSION_NAME
        }

        return JSONObject()
            .put("generated_at_ms", now)
            .put("generated_at_iso", iso(now))
            .put("event", JSONObject()
                .put("id", event.id)
                .put("type", event.eventType.name)
                .put("confidence", event.confidence)
                .put("created_at_ms", event.createdAtMs)
                .put("created_at_iso", iso(event.createdAtMs))
                .put("uploaded", event.uploaded)
            )
            .put("clip", JSONObject()
                .put("original_path", originalClip.absolutePath)
                .put("packaged_name", packagedClip.name)
                .put("size_bytes", originalClip.length())
            )
            .put("vehicle", JSONObject()
                .put("owner_id", pairing.pairedOwnerId)
                .put("vehicle_id", pairing.activeVehicleId.ifBlank { pairing.vehicleId })
                .put("role", pairing.activeRole)
            )
            .put("app", JSONObject()
                .put("package", packageName)
                .put("version", appVersion ?: "")
                .put("android_sdk_int", Build.VERSION.SDK_INT)
            )
    }

    private fun buildGpsGeoJson(timestampMs: Long): JSONObject {
        val points = JSONArray()
        val parked = parkedStateManager.snapshot()
        if (parked.parkedLat != null && parked.parkedLon != null) {
            points.put(
                JSONObject()
                    .put("source", "parked_state")
                    .put("timestamp_ms", parked.parkedAtMs ?: timestampMs)
                    .put("lat", parked.parkedLat)
                    .put("lon", parked.parkedLon)
            )
        }

        val lastKnown = bestLastKnownGps()
        if (lastKnown != null) {
            points.put(
                JSONObject()
                    .put("source", "device_last_known")
                    .put("timestamp_ms", lastKnown.timestampMs)
                    .put("lat", lastKnown.lat)
                    .put("lon", lastKnown.lon)
            )
        }

        val coordinates = JSONArray()
        for (i in 0 until points.length()) {
            val point = points.optJSONObject(i) ?: continue
            val lon = point.optDouble("lon", Double.NaN)
            val lat = point.optDouble("lat", Double.NaN)
            if (!lat.isNaN() && !lon.isNaN()) {
                coordinates.put(JSONArray().put(lon).put(lat))
            }
        }

        val geometry = if (coordinates.length() >= 2) {
            JSONObject()
                .put("type", "LineString")
                .put("coordinates", coordinates)
        } else if (coordinates.length() == 1) {
            JSONObject()
                .put("type", "Point")
                .put("coordinates", coordinates.getJSONArray(0))
        } else {
            JSONObject().put("type", "GeometryCollection").put("geometries", JSONArray())
        }

        return JSONObject()
            .put("type", "FeatureCollection")
            .put("features", JSONArray().put(
                JSONObject()
                    .put("type", "Feature")
                    .put("properties", JSONObject()
                        .put("event_timestamp_ms", timestampMs)
                        .put("point_count", coordinates.length())
                        .put("points", points)
                    )
                    .put("geometry", geometry)
            ))
    }

    private fun bestLastKnownGps(): GpsPoint? {
        val hasFine = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasFine && !hasCoarse) return null

        val manager = getSystemService(android.location.LocationManager::class.java) ?: return null
        val providers = runCatching { manager.getProviders(true) }.getOrDefault(emptyList())
        val location = providers
            .mapNotNull { provider -> runCatching { manager.getLastKnownLocation(provider) }.getOrNull() }
            .maxByOrNull { it.time }
            ?: return null

        return GpsPoint(location.latitude, location.longitude, location.time)
    }

    private fun zipFiles(files: List<File>, target: File) {
        ZipOutputStream(FileOutputStream(target)).use { zip ->
            files.forEach { file ->
                val entry = ZipEntry(file.name).apply { time = file.lastModified() }
                zip.putNextEntry(entry)
                FileInputStream(file).use { input -> input.copyTo(zip) }
                zip.closeEntry()
            }
        }
    }

    private fun sha256Hex(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        FileInputStream(file).use { input ->
            val buffer = ByteArray(8 * 1024)
            while (true) {
                val read = input.read(buffer)
                if (read <= 0) break
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }

    private fun hmacSha256Base64(payload: String): String {
        val keyBytes = evidenceKeyBytes()
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(keyBytes, "HmacSHA256"))
        val signature = mac.doFinal(payload.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(signature, Base64.NO_WRAP)
    }

    private fun evidenceKeyBytes(): ByteArray {
        val prefs = getSharedPreferences(EVIDENCE_PREFS, MODE_PRIVATE)
        val existing = prefs.getString(KEY_EVIDENCE_SECRET_B64, null)
        if (!existing.isNullOrBlank()) {
            return Base64.decode(existing, Base64.DEFAULT)
        }

        val key = ByteArray(32).also { SecureRandom().nextBytes(it) }
        prefs.edit().putString(KEY_EVIDENCE_SECRET_B64, Base64.encodeToString(key, Base64.NO_WRAP)).apply()
        return key
    }

    private fun evidenceKeyId(): String {
        val digest = MessageDigest.getInstance("SHA-256").digest(evidenceKeyBytes())
        return digest.joinToString("") { "%02x".format(it) }.take(16)
    }

    private fun iso(ms: Long): String {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).format(Date(ms))
    }

    private data class GpsPoint(
        val lat: Double,
        val lon: Double,
        val timestampMs: Long
    )

    private data class EvidenceExportResult(
        val shareFile: File,
        val exportName: String
    )

    companion object {
        private const val EVIDENCE_PREFS = "evidence_signing"
        private const val KEY_EVIDENCE_SECRET_B64 = "secret_b64"
    }
}
