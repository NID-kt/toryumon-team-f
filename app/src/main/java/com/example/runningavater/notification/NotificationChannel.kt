package com.example.runningavater.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService

fun createNotificationChannels(
    context: Context,
    bearname: String,
)  {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // クマの名前の変化のチャンネル
        val name = "${bearname}の変化"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel("bear_change", name, importance)
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        val notificationManager = getSystemService(context, NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }
}
