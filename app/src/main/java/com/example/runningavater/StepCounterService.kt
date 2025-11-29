package com.example.runningavater

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.datastore.preferences.core.edit
import com.example.runningavater.db.StepDate
import com.example.runningavater.home.toEpochMillis
import com.example.runningavater.notification.notify20Goal
import dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import lastNotify100GoalSentDateKey
import lastNotify20GoalSentDateKey
import lastNotify40GoalSentDateKey
import lastNotify50GoalSentDateKey
import lastNotify60GoalSentDateKey
import lastNotify80GoalSentDateKey
import targetSteps
import java.time.LocalDate
import java.time.LocalDateTime

fun startStepCounterService(context: Context) {
    val intent = Intent(context, StepCounterService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else {
        context.startService(intent)
    }
}
class StepCounterService : Service() {
    private lateinit var sensorManager: SensorManager
    private var stepSensor: Sensor? = null
    private var totalSteps = 0

    override fun onCreate() {
        super.onCreate()

//        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//        if (stepSensor == null) {
//            Log.e("StepCounterService", "Step Counter sensor not available!")
//            stopSelf()
//        }
//
//
//        createNotificationChannel()
//
//
//        val notification = NotificationCompat.Builder(this, "step_service_channel")
//            .setContentTitle("Step Counter Service")
//            .setContentText("Counting your steps...")
//            .setSmallIcon(R.drawable.ic_steps)
//            .build()
//
//        startForeground(1, notification)
    }

    val coroutineScope = CoroutineScope(SupervisorJob())
    val walkcount = Walkcount(this, coroutineScope)

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        // 権限チェック - 権限がない場合はサービスを停止
        if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            stopSelf()
            return START_NOT_STICKY
        }
        createNotificationChannel()
        val notification =
            NotificationCompat.Builder(this, "step_service_channel")
                .setContentTitle("Step Counter Service")
                .setContentText("Counting your steps...")
                .setSmallIcon(R.drawable.app_icon_yellow)
                .build()
        ServiceCompat.startForeground(
            this,
            100,
            notification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_HEALTH
            } else {
                0
            },
        )
        startcount(this, walkcount)
//        stepSensor?.let {
//            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
//        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopcount(this, walkcount)
        // sensorManager.unregisterListener(this)
        coroutineScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? = null

//    override fun onSensorChanged(event: SensorEvent?) {
//        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
//            val steps = event.values[0].toInt()
//            Log.d("StepCounterService", "Steps: $steps")
//            totalSteps = steps
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    "step_service_channel",
                    "Step Counter Service",
                    NotificationManager.IMPORTANCE_LOW,
                )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }
}

fun startcount(
    context: Context,
    walkcount: Walkcount,
) {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
    sensorManager.registerListener(walkcount, sensor, SensorManager.SENSOR_DELAY_NORMAL)
}

class Walkcount(val context: Context, val coroutineScope: CoroutineScope) : SensorEventListener {
    override fun onSensorChanged(p0: SensorEvent?) {
        coroutineScope.launch(Dispatchers.IO) {
            val app = context.applicationContext as MainApplication
            app.db.stepDateDao().insertAll(StepDate(id = 0, System.currentTimeMillis()))
            // 達成率の通知を送信する
            val now = LocalDateTime.now() // 2025/02/23 23:52:10.123

            val todayStart =
                now
                    .withHour(0) // 2025/02/23 00:52:10.123
                    .withMinute(0) // 2025/02/23 00:00:10.123
                    .withSecond(0) // 2025/02/23 00:00:00.123
                    .withNano(0) // 2025/02/23 00:00:00.000000

            val todayEnd =
                now
                    .withHour(23) // 2025/02/23 00:52:10.123
                    .withMinute(59) // 2025/02/23 00:00:10.123
                    .withSecond(59) // 2025/02/23 00:00:00.123
                    .withNano(999999999) // 2025/02/23 00:00:00.000000
            val targetSteps = context.dataStore.data.first()[targetSteps] ?: 0
            val todaySteps = app.db.stepDateDao().getTotalWalk(todayStart.toEpochMillis(), todayEnd.toEpochMillis())
            val achievementRate = todaySteps.toFloat() / targetSteps.toFloat() * 100
            when (achievementRate) {
                in 0f..<20f -> Unit
                in 20f..<40f -> {
                    val lastNotify20GoalSentDate = context.dataStore.data.first()[lastNotify20GoalSentDateKey] ?: 0
                    val toDay = LocalDate.now().toEpochDay()
                    if (lastNotify20GoalSentDate != toDay) {
                        notify20Goal(context)
                        context.dataStore.edit { settings ->
                            settings[lastNotify20GoalSentDateKey] = toDay
                        }
                    }
                }
                in 40f..<50f -> {
                    val lastNotify40GoalSentDate = context.dataStore.data.first()[lastNotify40GoalSentDateKey] ?: 0
                    val toDay = LocalDate.now().toEpochDay()
                    if (lastNotify40GoalSentDate != toDay) {
                        notify20Goal(context)
                        context.dataStore.edit { settings ->
                            settings[lastNotify40GoalSentDateKey] = toDay
                        }
                    }
                }
                in 50f..<60f -> {
                    val lastNotify50GoalSentDate = context.dataStore.data.first()[lastNotify50GoalSentDateKey] ?: 0
                    val toDay = LocalDate.now().toEpochDay()
                    if (lastNotify50GoalSentDate != toDay) {
                        notify20Goal(context)
                        context.dataStore.edit { settings ->
                            settings[lastNotify50GoalSentDateKey] = toDay
                        }
                    }
                }
                in 60f..<80f -> {
                    val lastNotify60GoalSentDate = context.dataStore.data.first()[lastNotify60GoalSentDateKey] ?: 0
                    val toDay = LocalDate.now().toEpochDay()
                    if (lastNotify60GoalSentDate != toDay) {
                        notify20Goal(context)
                        context.dataStore.edit { settings ->
                            settings[lastNotify60GoalSentDateKey] = toDay
                        }
                    }
                }
                in 80f..<100f -> {
                    val lastNotify80GoalSentDate = context.dataStore.data.first()[lastNotify80GoalSentDateKey] ?: 0
                    val toDay = LocalDate.now().toEpochDay()
                    if (lastNotify80GoalSentDate != toDay) {
                        notify20Goal(context)
                        context.dataStore.edit { settings ->
                            settings[lastNotify80GoalSentDateKey] = toDay
                        }
                    }
                }
                in 100f..Float.POSITIVE_INFINITY -> {
                    val lastNotify100GoalSentDate = context.dataStore.data.first()[lastNotify100GoalSentDateKey] ?: 0
                    val toDay = LocalDate.now().toEpochDay()
                    if (lastNotify100GoalSentDate != toDay) {
                        notify20Goal(context)
                        context.dataStore.edit { settings ->
                            settings[lastNotify100GoalSentDateKey] = toDay
                        }
                    }
                }
            }


        }
    }

    override fun onAccuracyChanged(
        p0: Sensor?,
        p1: Int,
    ) {
    }
}

fun stopcount(
    context: Context,
    walkcount: Walkcount,
) {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    sensorManager.unregisterListener(walkcount)
}
