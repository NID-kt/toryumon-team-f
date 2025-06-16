package com.example.runningavater.initialFlow

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

fun NavGraphBuilder.initialFlow(navController: NavHostController) {
    composable(route = "initialFlow/1",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow1Screen(navController = navController)
    }
    composable(route = "initialFlow/2",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow2Screen(navController = navController)
    }
    composable(route = "initialFlow/3",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow3Screen(navController = navController)
    }
    composable(route = "initialFlow/4",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow4Screen(navController = navController)
    }
    composable(route = "initialFlow/5",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow5Screen(
            navController = navController,
        )
    }
    composable(route = "initialFlow/6/{bearName}", arguments = listOf(navArgument("bearName") { type = NavType.StringType }),
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        val bearName = it.arguments?.getString("bearName") ?: error("bearName is null")
        InitialFlow6Screen(
            bearName = bearName,
            navController = navController,
        )
    }
    composable(route = "initialFlow/7",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow7Screen(navController = navController)
    }
    composable(route = "initialFlow/8",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow8Screen(
            navController = navController,
        )
    }
    composable(route = "initialFlow/9",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow9Screen(navController = navController)
    }
    composable(route = "initialFlow/10",
        enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(1000))},
        exitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left,tween(1000))},
        popEnterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        popExitTransition = {slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right,tween(1000))},
        ) {
        InitialFlow10Screen(
            navController = navController,
        )
    }
}
