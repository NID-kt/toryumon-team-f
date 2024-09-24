package com.example.runningavater

import GrowthScreen
import SettingPage
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
//import org.w3c.dom.Text
import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import java.util.*

// この画面はテストなので、表示する画面に今後すり替えてください
@Composable
fun TestScreen1() {
    Bear3D(assetFileLocation = "fatBearFinal3.glb")
}

@Composable
fun TestScreen2() {
    Text(text = "テスト画面2")
}

@Composable
fun TestScreen3() {
    Text(text = "テスト画面３")
}

// メイン画面のタブ
enum class MainScreenTab(
    val icon: ImageVector, // 画像ベクトル
    val label: String, // ラベル
) {
    Home(
        icon = Icons.Default.Home,
        label = "ホーム",
    ),
    List(
        icon = Icons.Default.KeyboardArrowUp,
        label = "成長",
    ),
    Settings(
        icon = Icons.Filled.Settings,
        label = "設定",
    ),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {//メイン画面
    var selectedItem by remember { mutableIntStateOf(0) }

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
        //RunningAvaterTheme {
        topBar = {//ヘッダーの表示
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEDA02C),
                    titleContentColor = MaterialTheme.colorScheme.background,
                ),
                title = {
                    // 日付と曜日を表示
                    Text(text = currentDate.value)
                }
            )

        },
        //ボトムバー
        //ホーム・設定・成長の遷移をする
        bottomBar = {
            NavigationBar (
                containerColor = Color(0xFFEDA02C),
            ){

                MainScreenTab.values().forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF080026), // 選択されたアイコンの色
                            unselectedIconColor = Color.White, // 未選択アイコンの色
                            selectedTextColor = Color(0xFF080026), // 選択されたラベルの色
                            //unselectedTextColor = Color(0xFFBDBDBD) // 未選択ラベルの色
                        )
                    )
                }
            }
        },
//        content = { innerPadding ->
//            // 空のコンテンツとしてBoxを使用
//            Box(modifier = Modifier.padding(innerPadding)) {
//                // 必要に応じてここにコンテンツを追加
//                TestScreen1()//くまちゃん3Dモデル表示
//
//            }
//        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                0 -> {
                    TestScreen1()// くまちゃん3Dモデルを表示する画面}
                    Text(
                        text = "35歩", // 追加したい文字
                        //modifier = Modifier.align(Alignment.Center),

                        modifier = Modifier
                            //.align(Alignment.Center)
                            .align(Alignment.TopCenter) // 上部中央に配置
                            .padding(top = 40.dp),
                        fontSize = 96.sp,
                        //fontWeight = FontWeight.Bold,
                        color = Color.White // テキストの色
                    )
                }
                1 -> GrowthScreen() //成長画面
                2 -> SettingPage() //設定画面
            }
        }
    }
}


// 現在の日付と曜日を取得する関数
//トップバーで使ってます
fun getCurrentDate(): String {
    // 日付と曜日をフォーマットする（例：09/11 (Wed)）
    val dateFormat = SimpleDateFormat("MM/dd (E)", Locale.getDefault())
    return dateFormat.format(Date())
}

//成長画面のぐるぐる回るチャート（多分）
@Composable
fun PieChart(modifier: Modifier = Modifier) {
    val pieEntryList =
        listOf(
            PieEntry(80f, ""),
            PieEntry(20f, ""),
        )

    val pieDataSet =
        PieDataSet(pieEntryList, "").apply {
            colors = listOf(Color.Green, Color.Red).map { it.toArgb() }
        }
    AndroidView(
        factory = { context ->
            com.github.mikephil.charting.charts.PieChart(context).apply {
                description = Description().apply { text = "" }
                centerText = "達成度"
                setEntryLabelTextSize(11f)
                data = PieData(pieDataSet).apply { setValueTextSize(20f) }
                // アニメーションを指定
                animateXY(1000, 1000)
                // 判例を非表示
                legend.isEnabled = false
                // 説明ラベルの非表示
                description.isEnabled = false
                invalidate()
            }
        },
        modifier = modifier.run { size(100.dp) },
    )
}

