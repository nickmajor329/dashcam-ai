package com.dashcam.ai.system

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.dashcam.ai.location.ParkedStateManager

class PowerStateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action ?: return
        val parkedStateManager = ParkedStateManager(context)

        when (action) {
            Intent.ACTION_POWER_DISCONNECTED -> {
                val location = bestLastKnownLocation(context)
                parkedStateManager.markParked(location?.latitude, location?.longitude)
            }

            Intent.ACTION_POWER_CONNECTED -> {
                parkedStateManager.markDriving()
            }
        }
    }

    private fun bestLastKnownLocation(context: Context): Location? {
        val hasFine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFine && !hasCoarse) return null

        val locationManager = context.getSystemService(LocationManager::class.java) ?: return null
        val providers = buildList {
            if (hasFine) add(LocationManager.GPS_PROVIDER)
            add(LocationManager.NETWORK_PROVIDER)
            add(LocationManager.PASSIVE_PROVIDER)
        }

        return providers
            .asSequence()
            .mapNotNull { provider -> runCatching { locationManager.getLastKnownLocation(provider) }.getOrNull() }
            .maxByOrNull { it.time }
    }
}
