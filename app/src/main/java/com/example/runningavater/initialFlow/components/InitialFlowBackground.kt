package com.example.runningavater.initialFlow.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.ui.theme.RunningAvaterTheme
import com.example.runningavater.ui.theme.SungYellow

@SuppressLint("ComposeParameterOrder")
@Composable
fun InitialFlowBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier
        .background(color = SungYellow)
        .fillMaxSize()) {
        content()
    }
}

@Preview
@Composable
private fun InitialFlowBackgroundPreview() {
    RunningAvaterTheme {
        InitialFlowBackground {
            NextButton(navController = rememberNavController(), nextDestination ="")
        }
    }
}
