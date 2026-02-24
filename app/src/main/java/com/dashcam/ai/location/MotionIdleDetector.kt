package com.dashcam.ai.location

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.SystemClock
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.abs

class MotionIdleDetector(
    context: Context,
    private val scope: CoroutineScope,
    private val idleThresholdMs: Long = 5 * 60_000L,
    private val onIdleDetected: () -> Unit,
    private val onMotionResumed: () -> Unit
) : SensorEventListener {

    private val sensorManager = context.getSystemService(SensorManager::class.java)
    private val accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var lastMotionAtMs: Long = SystemClock.elapsedRealtime()
    private var idleNotified = false
    private var monitorJob: Job? = null

    fun start() {
        accelerometer?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        monitorJob?.cancel()
        monitorJob = scope.launch {
            while (isActive) {
                val now = SystemClock.elapsedRealtime()
                val idleElapsed = now - lastMotionAtMs
                if (!idleNotified && idleElapsed >= idleThresholdMs) {
                    idleNotified = true
                    onIdleDetected()
                }
                delay(10_000L)
            }
        }
    }

    fun stop() {
        sensorManager?.unregisterListener(this)
        monitorJob?.cancel()
        monitorJob = null
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        val ax = event.values[0]
        val ay = event.values[1]
        val az = event.values[2]

        val motionScore = abs(ax) + abs(ay) + abs(az - GRAVITY_EARTH)
        if (motionScore >= MOTION_THRESHOLD) {
            lastMotionAtMs = SystemClock.elapsedRealtime()
            if (idleNotified) {
                idleNotified = false
                onMotionResumed()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    companion object {
        private const val GRAVITY_EARTH = 9.81f
        private const val MOTION_THRESHOLD = 2.4f
    }
}
