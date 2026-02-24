package com.dashcam.ai.alerts

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dashcam.ai.data.EventEntity
import com.dashcam.ai.pairing.PairingManager
import com.dashcam.ai.privacy.PrivacySettingsManager

class AlertDispatcher(private val context: Context) {

    private val routingManager = AlertRoutingManager(context)
    private val privacySettingsManager = PrivacySettingsManager(context)
    private val pairingManager = PairingManager(context)

    fun enqueueAlert(event: EventEntity) {
        val routing = routingManager.snapshot()
        val privacy = privacySettingsManager.snapshot()
        val pairing = pairingManager.snapshot()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<AlertUploadWorker>()
            .setConstraints(constraints)
            .setInputData(
                workDataOf(
                    AlertUploadWorker.KEY_EVENT_ID to event.id,
                    AlertUploadWorker.KEY_EVENT_TYPE to event.eventType.name,
                    AlertUploadWorker.KEY_CLIP_PATH to event.clipPath,
                    AlertUploadWorker.KEY_CREATED_AT_MS to event.createdAtMs,
                    AlertUploadWorker.KEY_OWNER_ID to pairing.pairedOwnerId,
                    AlertUploadWorker.KEY_VEHICLE_ID to pairing.vehicleId,
                    AlertUploadWorker.KEY_APP_ENABLED to routing.appEnabled,
                    AlertUploadWorker.KEY_EMAIL_ENABLED to routing.emailEnabled,
                    AlertUploadWorker.KEY_SMS_ENABLED to routing.smsEnabled,
                    AlertUploadWorker.KEY_APP_DEVICE_ID to routing.appDeviceId,
                    AlertUploadWorker.KEY_EMAIL_ADDRESS to routing.emailAddress,
                    AlertUploadWorker.KEY_PHONE_NUMBER to routing.phoneNumber,
                    AlertUploadWorker.KEY_BLUR_FACES to privacy.blurFaces,
                    AlertUploadWorker.KEY_BLUR_PLATES to privacy.blurPlates
                )
            )
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork("alert-${event.id}", ExistingWorkPolicy.REPLACE, request)
    }
}
