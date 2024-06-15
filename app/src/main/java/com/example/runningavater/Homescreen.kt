package com.example.runningavater

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.vector.ImageVector

//この画面はテストなので、表示する画面に今後すり替えてください
@Composable
fun TestScreen1() {
    Text(text = "テスト画面１")
}

@Composable
fun TestScreen2() {
    Text(text = "テスト画面２")
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
                2 -> TestScreen3() //ここに設定画面を表示
                else -> { /* do nothing */
                }
            }
        }
    }
}




