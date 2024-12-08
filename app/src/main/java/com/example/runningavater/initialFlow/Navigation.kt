package com.example.runningavater.initialFlow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.initialFlow(navController: NavHostController) {
    composable(route = "initialFlow/1") {
        InitialFlow1Screen(navController = navController)
    }
    composable(route = "initialFlow/2") {
        InitialFlow2Screen()
    }
    composable(route = "initialFlow/3") {
        InitialFlow3Screen(navController = navController)
    }
    composable(route = "initialFlow/4") {
        InitialFlow4Screen(navController = navController)
    }
    composable(route = "initialFlow/5") {
        InitialFlow5Screen()
    }
    composable(route = "initialFlow/6") {
        InitialFlow6Screen()
    }
    composable(route = "initialFlow/7") {
        InitialFlow7Screen(navController = navController)
    }
    composable(route = "initialFlow/8/{userName}") {
        InitialFlow8Screen()
    }
    composable(route = "initialFlow/9") {
        InitialFlow9Screen(navController = navController)
    }
    composable(route = "initialFlow/10") {
        InitialFlow10Screen()
    }
}
