package com.example.runningavater.initialFlow

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.R
import com.example.runningavater.initialFlow.components.BackButton
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.initialFlow.components.NextButton
import com.example.runningavater.ui.theme.RunningAvaterTheme

@Composable
fun InitialFlow9Screen(navController: NavController) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { }
    InitialFlowBackground {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(0.dp, 50.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "通知をオンにすると",
                    color = Color.Black,
                    fontSize = 32.sp,
                )
                Text(
                    text = "sampleの変化や目標の",
                    color = Color.Black,
                    fontSize = 32.sp,
                )
                Text(
                    text = "達成がすぐにわかるよ！",
                    color = Color.Black,
                    fontSize = 32.sp,
                )

                Image(
                    painter = painterResource(id = R.drawable.initialflow9img),
                    contentDescription = "くま見守ろう",
                    modifier =
                        Modifier
                            .offset((0.dp), (50.dp))
                            .size(400.dp),
                )
            }
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp, 0.dp, 20.dp, 90.dp),
            ) {
                NextButton(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            val permissionCheck = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                            val alreadyRequested = hasRequestedNotificationPermission(context)
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED && !alreadyRequested){
                                setRequestedNotificationPermission(context)
                                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                return@NextButton
                            }

                        }
                    },
                    navController = navController,
                    nextDestination = "InitialFlow/10",
                )
            }
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp),
            ) {
                BackButton(
                    navController = navController,
                )
            }
        }
    }
}
fun hasRequestedNotificationPermission(context: Context): Boolean {
    val prefs = context.getSharedPreferences("permissions", Context.MODE_PRIVATE)
    return prefs.getBoolean("notification_requested", false)
}

fun setRequestedNotificationPermission(context: Context) {
    val prefs = context.getSharedPreferences("permissions", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("notification_requested", true).apply()
}

@Preview
@Composable
private fun InitialFlow9Preview() {
    RunningAvaterTheme {
        InitialFlow9Screen(rememberNavController())
    }
}
