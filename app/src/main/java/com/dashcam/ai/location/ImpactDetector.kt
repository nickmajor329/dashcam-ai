package com.dashcam.ai.location

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import kotlin.math.abs

class ImpactDetector(
    context: Context,
    private val thresholdG: Float = 3.0f,
    private val cooldownMs: Long = 20_000L,
    private val onImpact: () -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(SensorManager::class.java)
    private val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var lastImpactAtMs = 0L

    fun start() {
        accelerometer?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stop() {
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val ax = event.values[0]
        val ay = event.values[1]
        val az = event.values[2]

        val total = abs(ax) + abs(ay) + abs(az)
        val gForceApprox = total / GRAVITY_EARTH
        val now = SystemClock.elapsedRealtime()

        if (gForceApprox >= thresholdG && now - lastImpactAtMs >= cooldownMs) {
            lastImpactAtMs = now
            onImpact()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    companion object {
        private const val GRAVITY_EARTH = 9.81f
    }
}
