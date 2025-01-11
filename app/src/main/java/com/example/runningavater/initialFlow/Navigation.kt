package com.example.runningavater.initialFlow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.initialFlow(navController: NavHostController) {
    composable(route = "initialFlow/1") {
        InitialFlow1Screen(navController = navController)
    }
    composable(route = "initialFlow/2") {
        InitialFlow2Screen(navController = navController)
    }
    composable(route = "initialFlow/3") {
        InitialFlow3Screen(navController = navController)
    }
    composable(route = "initialFlow/4") {
        InitialFlow4Screen(navController = navController)
    }
    composable(route = "initialFlow/5") {
        InitialFlow5Screen(
            navController = navController,
        )
    }
    composable(route = "initialFlow/6/{bearName}", arguments = listOf(navArgument("bearName") { type = NavType.StringType })) {
        val bearName = it.arguments?.getString("bearName") ?: error("bearName is null")
        InitialFlow6Screen(
            bearName = bearName,
            navController = navController,
        )
    }
    composable(route = "initialFlow/7") {
        InitialFlow7Screen(navController = navController)
    }
    composable(route = "initialFlow/8") {
        InitialFlow8Screen(
            navController = navController,
        )
    }
    composable(route = "initialFlow/9") {
        InitialFlow9Screen(navController = navController)
    }
    composable(route = "initialFlow/10") {
        InitialFlow10Screen(
            navController = navController,
        )
    }
}
