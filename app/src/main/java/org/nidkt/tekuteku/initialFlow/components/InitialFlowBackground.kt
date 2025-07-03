package org.nidkt.tekuteku.initialFlow.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.nidkt.tekuteku.ui.theme.RunningAvaterTheme
import org.nidkt.tekuteku.ui.theme.SungYellow

@SuppressLint("ComposeParameterOrder")
@Composable
fun InitialFlowBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
            modifier
                .background(color = SungYellow)
                .fillMaxSize()
                .safeDrawingPadding()
    ) {
        content()
    }
}

@Preview
@Composable
private fun InitialFlowBackgroundPreview() {
    RunningAvaterTheme {
        InitialFlowBackground {
            NextButton(navController = rememberNavController(), nextDestination = "")
        }
    }
}
