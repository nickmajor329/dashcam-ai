package com.dashcam.ai.system

import android.Manifest
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.dashcam.ai.location.ParkedStateManager

class BluetoothStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null) return
        if (!hasBluetoothPermission(context)) return

        val device: BluetoothDevice =
            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return
        val parkedStateManager = ParkedStateManager(context)
        val snapshot = parkedStateManager.snapshot()

        when (intent.action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                if (isLikelyCarDevice(device)) {
                    parkedStateManager.setKnownCarDeviceAddress(device.address)
                    parkedStateManager.markDriving()
                }
            }

            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                val known = snapshot.knownCarDeviceAddress
                val sameKnownDevice = !known.isNullOrBlank() && known == device.address
                val likelyCar = isLikelyCarDevice(device)

                if (sameKnownDevice || likelyCar) {
                    if (snapshot.isParked) {
                        parkedStateManager.setOwnerAwayEnabled(true)
                    }
                }
            }
        }
    }

    private fun hasBluetoothPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) ==
                PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun isLikelyCarDevice(device: BluetoothDevice): Boolean {
        val deviceClass = device.bluetoothClass?.deviceClass
        if (deviceClass == BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO) return true

        val name = device.name?.lowercase().orEmpty()
        val hints = listOf("car", "sync", "uconnect", "handsfree", "mazda", "toyota", "honda", "ford")
        return hints.any { it in name }
    }
}
