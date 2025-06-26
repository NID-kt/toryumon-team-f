package com.example.runningavater.home


import android.Manifest

import afterLevelKey

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.datastore.preferences.core.edit
import beforeLevelKey
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.runningavater.MainApplication
import com.example.runningavater.R
import com.example.runningavater.StepCounterService
import com.example.runningavater.authentication.LifecycleResumeEffect
import com.example.runningavater.ui.theme.NuclearMango
import dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    // 現在の日付と曜日を保持するState
    val currentDate = remember { mutableStateOf(getCurrentDate()) }

    // 日時を更新するためのLaunchedEffect
    LaunchedEffect(Unit) {
        while (true) {
            currentDate.value = getCurrentDate()
            kotlinx.coroutines.delay(60000L) // 1分ごとに更新
        }
    }
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(context.checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) }

    LifecycleResumeEffect {
        hasPermission = (context.checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED)
            .also { hasPermission ->
                if (hasPermission) {
                    val intent = Intent(context, StepCounterService::class.java)
                    context.startForegroundService(intent)
                }
            }

    }

    val stepcount = remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(key1 = Unit) {
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
        launch(Dispatchers.IO) {
            val app = context.applicationContext as MainApplication
            stepcount.value = app.db.stepDateDao().getTotalWalk(todayStart.toEpochMillis(), todayEnd.toEpochMillis())
        }
    }
    Scaffold(
        contentWindowInsets = WindowInsets(left = 0),
        topBar = {
            // ヘッダーの表示
            TopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = NuclearMango,
                        titleContentColor = MaterialTheme.colorScheme.background,
                    ),
                title = {
                    // 日付と曜日を表示
                    Text(text = currentDate.value)
                },
            )
        },
    ) {
        Box(Modifier.padding(it)) {
            Bear3D(assetFileLocation = "fatBear.glb")
            if (!hasPermission) {
                PermissionAlert(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(12.dp)
                )
            }
            Row(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SetImage(
                    fileName = "flowers_big.PNG",
                    modifier = Modifier.size(120.dp),
                )

                // 歩数の数字の部分
                Text(
                    stepcount.value.toString(),
                    fontSize = 96.sp,
                    color = NuclearMango,
                )
                // 調整のためのスペース挿入
                Spacer(Modifier.width(11.dp))
                // ”歩”の文字
                Text(
                    text = "歩",
                    fontSize = 32.sp,
                    color = NuclearMango,
                )
                SetImage(
                    fileName = "flowers_right.PNG",
                    modifier = Modifier.size(120.dp),
                )
            }
        }
    }
    var isOpen by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val beforeLevel = context.dataStore.data.first()[beforeLevelKey] ?: 2
        val afterLevel = context.dataStore.data.first()[afterLevelKey] ?: 2

        isOpen = beforeLevel != afterLevel
        context.dataStore.edit {
            it[beforeLevelKey] = afterLevel
        }
    }
    if (isOpen) {
        Dialog(onDismissRequest = {}) {
            Box {
                Column {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.revelup))
                    LottieAnimation(
                        composition, modifier = Modifier
                            .weight(1f)
                    )

                    Button(onClick = { isOpen = false }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(
                            text = "閉じる",
                        )
                    }
                }
                Text(
                    text = "Level Up!",
                    color = NuclearMango,
                    fontSize = 48.sp,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = 181.dp),
                )
            }
        }
    }

}

// 現在の日付と曜日を取得する関数
// トップバーで使ってます
fun getCurrentDate(): String {
    // 日付と曜日をフォーマットする（例：09/11 (Wed)）
    val dateFormat = SimpleDateFormat("MM/dd (E)", Locale.getDefault())
    return dateFormat.format(Date())
}

@Composable
fun SetImage(
    fileName: String,
    modifier: Modifier = Modifier,
) {
    // assets フォルダから画像をロード
    val context = LocalContext.current
    val assetManager = context.assets
    val bitmap = BitmapFactory.decodeStream(assetManager.open(fileName))
    // 画像を表示
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        modifier = modifier,
    )
}

fun LocalDateTime.toEpochMillis(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return this.atZone(zoneId).toInstant().toEpochMilli()
}
