package com.example.runningavater.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.runningavater.R

fun notifySlim(
    context: Context,
    bearname: String,
) {
    var builder =
        NotificationCompat.Builder(context, "bear_change")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("${bearname}からの報告")
            .setContentText("${bearname}が呼んでます！")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(1, builder.build())
    }
}

fun notyfyFat(
    context: Context,
    bearname: String,
) {
    var builder =
        NotificationCompat.Builder(context, "bear_change")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("${bearname}からの報告")
            .setContentText("${bearname}が呼んでます....")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@with
        }
        // notificationId is a unique int for each notification that you must define.
        notify(1, builder.build())
    }
}
