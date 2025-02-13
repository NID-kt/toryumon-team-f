package com.example.runningavater.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService

fun createNotificationChannels(
    context: Context,
    bearname: String,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager = getSystemService(context, NotificationManager::class.java) as NotificationManager

        // クマの名前の変化のチャンネル
        val changeChannel =
            NotificationChannel(
                "bear_change",
                "${bearname}の変化",
                NotificationManager.IMPORTANCE_HIGH,
            )
        val regularlyChannel =
            NotificationChannel(
                "regularly_notification",
                "定期的な通知",
                NotificationManager.IMPORTANCE_DEFAULT,
            )
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        notificationManager.createNotificationChannel(changeChannel)
        notificationManager.createNotificationChannel(regularlyChannel)
    }
}
