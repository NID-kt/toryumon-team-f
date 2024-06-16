package com.example.runningavater

import android.Manifest
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.runningavater.ui.theme.RunningAvaterTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update




@OptIn(ExperimentalPermissionsApi::class)
class RunningAvatar : ComponentActivity(), SensorEventListener {
    lateinit var sensorManager: SensorManager
    var targetSensor: Sensor? = null
    val stepCountFlow = MutableStateFlow(0)
    val walkingFlgFlow = MutableStateFlow(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        println("sensorList:")
        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach {
            println(it)
        }
        targetSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if(targetSensor == null) {
            println("targetSensor is null")
        } else {
            println("targetSensor:")
            println(targetSensor)
        }

        setContent {
            val stepCount = stepCountFlow.collectAsState().value
            val walkingFlg = walkingFlgFlow.collectAsState().value
            val activityRecoginitionPermission = rememberPermissionState(permission = Manifest.permission.ACTIVITY_RECOGNITION)
            RunningAvaterTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(activityRecoginitionPermission.status.isGranted) {
                        Bear3D(assetFileLocation ="fatBear.glb")
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "${stepCount}歩きました。",
                                style = TextStyle(fontSize = 28.sp)
                            )
                            Text(if(walkingFlg) "歩いています。" else "歩いていません。")
                            Button(onClick = {
                                startWalking()
                            }) {
                                Text("Start Walking")
                            }
                            Button(onClick = {
                                stopWalking()
                            }) {
                                Text("Stop Walking")
                            }
                        }
                    } else {
                        SideEffect {
                            activityRecoginitionPermission.launchPermissionRequest()
                        }
                    }
                }
            }
        }
    }

    fun startWalking() {
        println("startWalking")
        walkingFlgFlow.update { true }
        sensorManager.registerListener(this, targetSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun stopWalking() {
        println("stopWalking")
        walkingFlgFlow.update { false }
        sensorManager.unregisterListener(this)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
//        TODO("Not yet implemented")
        println("onSensorChanged")
        println(p0?.values?.get(0) ?: "null")
        stepCountFlow.update {
            println("stepCountFlow.update")
            it + 1
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
//        TODO("Not yet implemented")
    }
}




