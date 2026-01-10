package com.roomie.app.core.data.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.roomie.app.MainActivity
import com.roomie.app.R

class NotificationHelper(private val context: Context) {

    companion object {
        private const val CHANNEL_ID = "roomie_notifications_channel"
        private const val CHANNEL_NAME = "Notificações Roomie"
        private const val CHANNEL_DESCRIPTION = "Receba notificações sobre matches e atualizações"
        private const val NOTIFICATION_ID_BASE = 1000

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = CHANNEL_DESCRIPTION
                    enableVibration(true)
                    enableLights(true)
                }

                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    fun showNotification(mensagem: String, notificationId: Int? = null) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info) 
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(mensagem)
            .setStyle(NotificationCompat.BigTextStyle().bigText(mensagem)) 
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        
        try {
            val id = notificationId ?: (System.currentTimeMillis().toInt() % 10000)
            notificationManager.notify(id, notification)
        } catch (e: SecurityException) {
            android.util.Log.e("NotificationHelper", "Erro ao exibir notificação: permissão negada", e)
        }
    }
}

