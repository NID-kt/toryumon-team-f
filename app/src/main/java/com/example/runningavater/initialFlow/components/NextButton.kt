package com.example.runningavater.initialFlow.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.ui.theme.RunningAvaterTheme

@Composable
fun NextButton(navController: NavHostController, nextDestination: String) {
    Button(
        onClick = { navController.navigate(nextDestination) },
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = "次へ",
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
private fun NextbuttonPreview() {
    RunningAvaterTheme {
        NextButton(navController = rememberNavController(), nextDestination = "")
    }
}
