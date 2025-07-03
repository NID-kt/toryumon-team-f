package org.nidkt.tekuteku

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.nidkt.tekuteku.db.StepDate

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
