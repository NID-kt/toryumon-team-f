package com.example.runningavater

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.authentication.AuthenticationScreen
import com.example.runningavater.growth.GrowthScreen
import com.example.runningavater.home.HomeScreen
import com.example.runningavater.initialFlow.initialFlow
import com.example.runningavater.settings.GoalSettingsScreen
import com.example.runningavater.settings.ProfileScreen
import com.example.runningavater.settings.SettingsScreen
import com.example.runningavater.settings.SpanSettingsScreen
import com.example.runningavater.ui.theme.NuclearMango

fun getStartDestination(context: Context): String {
    val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val isFirstLaunch = prefs.getBoolean("is_first_launch", true)

    if (isFirstLaunch) {
        return "initialFlow/1"
    } else {
        return "authentication"
    }
}

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    // startDestination: String = "initialFlow/1", // メイン画面をスタート画面に設定
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context = LocalContext.current
    val startDestination = getStartDestination(context)
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
//    val startDestination = if (true) "initialFlow/2" else "authentication"
    Scaffold(
        bottomBar = {
            if (currentDestination?.route?.startsWith("initialFlow") != true) {
                MainBottomBar(currentDestination, navController)
            }
        },
    ) { paddingValues ->
        // ナビゲーションホストを作成
        NavHost(navController = navController, startDestination = startDestination, modifier = Modifier.padding(paddingValues)) {
            composable("authentication") {
                AuthenticationScreen(navController = navController) // 認証画面を表示
            }
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
                ProfileScreen(navController,
                    profileImageUri = profileImageUri,
                    onImageSelected = { newUri ->
                        profileImageUri = newUri
                    }) // プロフィール画面を表示
            }
            composable(route = "settings/spansettings") {
                SpanSettingsScreen() // 期間設定画面を表示
            }
            composable(route = "settings/goalsettings") {
                GoalSettingsScreen(navController) // 歩数画面を表示
            }
            initialFlow(navController)
        }
    }
}

@Composable
private fun MainBottomBar(
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    NavigationBar(
        containerColor = NuclearMango,
    ) {
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
                colors =
                NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.White.copy(alpha = 0.5f),
                    unselectedTextColor = Color.White.copy(alpha = 0.5f),
                    indicatorColor = Color.White.copy(alpha = 0.3f),
                ),
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

val Context.settingsDataStore by preferencesDataStore(name = "settings")

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
