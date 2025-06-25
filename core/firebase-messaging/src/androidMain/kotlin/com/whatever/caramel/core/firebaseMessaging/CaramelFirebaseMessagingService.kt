package com.whatever.caramel.core.firebaseMessaging

import android.Manifest
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.UUID

class CaramelFirebaseMessagingService : FirebaseMessagingService() {

    private val fcmTokenProvider: FcmTokenProvider by inject()
    private val notificationIntent: NotificationIntentProvider by inject()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        scope.launch {
            try {
                fcmTokenProvider.updateToken(token = token)
            } catch (e: Exception) {
                Napier.e { "FCM 토큰 업데이트 실패: ${e.message}" }
            }
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val notification = message.notification ?: return

        val intent = notificationIntent.provideNotificationIntent(context = applicationContext)

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            UUID.randomUUID().hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, getString(R.string.fcm_id_01))
            .setSmallIcon(R.drawable.ic_notification_small)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_notification))
            .setColor(getColor(R.color.notification_color))
            .setColorized(true)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(this).notify(
            UUID.randomUUID().hashCode(),
            builder.build()
        )
    }

}
