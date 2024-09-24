package com.example.runningavater.initialFlow

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.initialFlow() {
    composable(route = "initialFlow/1") {
        InitialFlow1Screen()
    }
    composable(route = "initialFlow/2") {
        InitialFlow2Screen()
    }
    composable(route = "initialFlow/3") {
        InitialFlow3Screen()
    }
    composable(route = "initialFlow/4") {
        InitialFlow4Screen()
    }
    composable(route = "initialFlow/5") {
        InitialFlow5Screen()
    }
    composable(route = "initialFlow/6") {
        InitialFlow6Screen()
    }
    composable(route = "initialFlow/7") {
        InitialFlow7Screen()
    }
    composable(route = "initialFlow/8") {
        InitialFlow8Screen()
    }
    composable(route = "initialFlow/9") {
        InitialFlow9Screen()
    }
    composable(route = "initialFlow/10") {
        InitialFlow10Screen()
    }
}

