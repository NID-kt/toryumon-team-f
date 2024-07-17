package com.example.runningavater

import GrowthScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyAppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "main", // メイン画面をスタート画面に設定
) {
    // ナビゲーションホストを作成
    NavHost(navController = navController, startDestination = startDestination) {
        composable("main") {
            MainScreen() // メイン画面を表示
        }
        composable(route = "growth") {
            GrowthScreen() // 成長画面を表示
        }
    }
}
