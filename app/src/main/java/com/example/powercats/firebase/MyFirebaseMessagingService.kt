package com.example.powercats.firebase

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.powercats.ui.activities.AlertDetailComposableActivity
import com.example.powercats.ui.model.AlertUi
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

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
        Log.d(TAG, "Mensagem recebida do tópico: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Data payload: ${remoteMessage.data}")

            remoteMessage.data["alert"]?.let { json ->
                try {
                    val alert = Gson().fromJson(json, AlertUi::class.java)
                    Log.d(TAG, "Alerta recebido: $alert")

                    showNotification(
                        title = "🚨 Alerta ${alert.alertLevel}",
                        message = alert.description,
                        alert = alert,
                    )
                    return
                } catch (e: Exception) {
                    Log.e(TAG, "Erro ao desserializar alerta: ${e.message}")
                }
            }

            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            showNotification(title, body)
            return
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        FirebaseMessaging
            .getInstance()
            .subscribeToTopic(TOPIC_ALERTS)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Inscrito no tópico '$TOPIC_ALERTS'")
                } else {
                    Log.w(TAG, "Falha ao inscrever no tópico '$TOPIC_ALERTS'", task.exception)
                }
            }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private fun showNotification(
        title: String?,
        message: String?,
        alert: AlertUi? = null,
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
        }

        val pendingIntent =
            alert?.let {
                val intent =
                    Intent(this, AlertDetailComposableActivity::class.java).apply {
                        putExtra("alert", it)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
            }

        val builder =
            NotificationCompat
                .Builder(this, CHANNEL_ID)
                .setContentTitle(title ?: "Novo alerta")
                .setContentText(message ?: "")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setAutoCancel(true)
                .apply {
                    if (pendingIntent != null) setContentIntent(pendingIntent)
                }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            builder.priority = NotificationCompat.PRIORITY_HIGH
        }

        NotificationManagerCompat
            .from(this)
            .notify(System.currentTimeMillis().toInt(), builder.build())
    }
}
