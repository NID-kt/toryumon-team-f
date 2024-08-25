package com.example.runningavater


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.growth.GrowthScreen
import com.example.runningavater.home.HomeScreen
import com.example.runningavater.settings.GoalSettingsScreen
import com.example.runningavater.settings.ProfileScreen
import com.example.runningavater.settings.SettingsScreen
import com.example.runningavater.settings.SpanSettingsScreen

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "home", // メイン画面をスタート画面に設定
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            MainBottomBar(currentDestination, navController)
        },
    ) { paddingValues ->
        // ナビゲーションホストを作成
        NavHost(navController = navController, startDestination = startDestination, modifier = Modifier.padding(paddingValues)) {
            composable("home") {
                HomeScreen() // メイン画面を表示
            }
            composable(route = "growth") {
                GrowthScreen() // 成長画面を表示
            }
            composable(route = "settings") {
                SettingsScreen(navController, profileImageUri = null) // 設定画面を表示
            }
            composable(route = "settings/profile") {
                ProfileScreen(navController) // プロフィール画面を表示
            }
            composable(route = "settings/spansettings") {
                SpanSettingsScreen() // 期間設定画面を表示
            }
            composable(route = "settings/goalsettings") {
                GoalSettingsScreen() // 歩数画面を表示
            }
        }
    }

}

@Composable
private fun MainBottomBar(
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBar {
        MainScreenTab.entries.forEachIndexed { _, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }
    }
}

enum class MainScreenTab(
    val icon: ImageVector, // 画像ベクトル
    val label: String, // ラベル
    val route: String,
) {
    Home(
        icon = Icons.Default.Home,
        label = "ホーム",
        route = "home",
    ),
    Growth(
        icon = Icons.Default.KeyboardArrowUp,
        label = "成長",
        route = "growth",
    ),
    Settings(
        icon = Icons.Filled.Settings,
        label = "設定",
        route = "settings",
    ),
}
