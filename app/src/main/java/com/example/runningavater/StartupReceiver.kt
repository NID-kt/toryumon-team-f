package com.example.runningavater

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class StartupReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        if (context!!.checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            startStepCounterService(context)
        }
    }
}
