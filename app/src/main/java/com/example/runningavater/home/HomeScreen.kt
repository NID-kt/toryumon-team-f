package com.example.runningavater.home

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.runningavater.ui.theme.NuclearMango
import java.text.SimpleDateFormat
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
    Scaffold(
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
                    text = "35",
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
