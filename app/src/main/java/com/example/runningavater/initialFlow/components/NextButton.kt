package com.example.runningavater.initialFlow.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.ui.theme.RunningAvaterTheme

@Composable
fun NextButton(
    navController: NavController,
    nextDestination: String,
    modifier: Modifier = Modifier,
    text: String = "次へ",
    onClick: (() -> Unit)? = null
) {
    Button(
        onClick = { navController.navigate(nextDestination) },
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
        )
    }
}

@Preview
@Composable
private fun NextbuttonPreview() {
    RunningAvaterTheme {
        NextButton(navController = rememberNavController(),
            nextDestination = "")
    }
}
