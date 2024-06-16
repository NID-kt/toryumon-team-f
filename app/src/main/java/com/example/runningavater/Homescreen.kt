package com.example.runningavater

import SettingPage
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

//この画面はテストなので、表示する画面に今後すり替えてください
@Composable
fun TestScreen1() {
    Bear3D(assetFileLocation = "fatBear.glb")

}

@Composable
fun TestScreen2() {
    Text(text = "テスト画面2")
}

@Composable
fun TestScreen3() {
    Text(text = "テスト画面３")
}
enum class MainScreenTab( // メイン画面のタブ
    val icon: ImageVector, // 画像ベクトル
    val label: String // ラベル
) {
    Home(
        icon = Icons.Default.Home,
        label = "ホーム"
    ),
    List(
        icon = Icons.Default.KeyboardArrowUp,
        label = "成長"
    ),
    Settings(
        icon = Icons.Filled.Settings,
        label = "設定"
    ),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() { // メイン画面にナビゲーションバーを表示
    var selectedItem by remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                MainScreenTab.values().forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedItem) {
                0 -> TestScreen1() //ここにホーム画面を表示
                1 -> TestScreen2() //ここに成長画面を表示
                2 -> SettingPage() //ここに設定画面を表示
                else -> { /* do nothing */
                }
            }
        }
    }
}

@Composable
fun PieChart(modifier: Modifier = Modifier) {
    val pieEntryList = listOf(
        PieEntry(80f,""),
        PieEntry (20f,"")
    )

    val pieDataSet = PieDataSet(pieEntryList, "").apply {
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
                //判例を非表示
                legend.isEnabled = false
                //説明ラベルの非表示
                description.isEnabled = false
                invalidate()
            }
        },
        modifier = modifier.run { size(100.dp) }
    )
}



