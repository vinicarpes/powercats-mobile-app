package com.example.powercats.firebase

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "FCM"
        private const val CHANNEL_ID = "alerts_channel"
        private const val TOPIC_ALERTS = "alerts"
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this as? android.app.Activity ?: return,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1,
            )
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "onMessageReceived called")
        Log.d(TAG, "From: ${remoteMessage.from}")

        remoteMessage.notification?.let {
            Log.d(TAG, "Notification payload - Title: ${it.title}, Body: ${it.body}")
            showNotification(it.title, it.body)
        }

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Data payload: ${remoteMessage.data}")
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            showNotification(title, body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Novo token FCM: $token")

        FirebaseMessaging
            .getInstance()
            .subscribeToTopic(TOPIC_ALERTS)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "✅ Inscrito no tópico '$TOPIC_ALERTS' com token $token")
                } else {
                    Log.w(TAG, "⚠️ Falha ao inscrever no tópico '$TOPIC_ALERTS'", task.exception)
                }
            }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(
        title: String?,
        message: String?,
    ) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            notificationManager.getNotificationChannel(CHANNEL_ID) == null
        ) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Alertas",
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    description = "Canal para alertas do aplicativo"
                    enableLights(true)
                    enableVibration(true)
                }
            notificationManager.createNotificationChannel(channel)
            Log.d(TAG, "Canal de notificação '$CHANNEL_ID' criado")
        }

        val builder =
            NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setContentTitle(title ?: "Novo alerta")
                .setContentText(message ?: "")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.priority = NotificationCompat.PRIORITY_HIGH
        }

        val notification = builder.build()
        NotificationManagerCompat
            .from(this)
            .notify(System.currentTimeMillis().toInt(), notification)

        Log.d(TAG, "Notificação exibida - Title: ${title ?: "Novo alerta"}, Body: ${message ?: ""}")
    }
}
