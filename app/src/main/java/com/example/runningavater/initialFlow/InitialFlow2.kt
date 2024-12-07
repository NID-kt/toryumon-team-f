package com.example.runningavater.initialFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.R
import com.example.runningavater.initialFlow.components.BackButton
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.initialFlow.components.NextButton
import com.example.runningavater.ui.theme.RunningAvaterTheme

@Composable
fun InitialFlow2Screen(navController: NavHostController) {
    InitialFlowBackground {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(0.dp, 50.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "可愛いアバターと一緒に\n楽しくダイエット",
                    color = Color.Black,
                    fontSize = 32.sp,
                    lineHeight = 46.3.sp,
                    textAlign = TextAlign.Center,
                )
            }
            val image = painterResource(R.drawable.initialflow2)
            Image(
                painter = image,
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(image.intrinsicSize.width / image.intrinsicSize.height)
                        .align(Alignment.Center),
            )

            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(0.dp, 0.dp, 0.dp, 80.dp),
            ) {
                NextButton(
                    navController = navController,
                    nextDestination = "initialFlow/5",
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp),
                )
            }
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp),
            ) {
                BackButton(
                    navController = navController,
                )
            }
        }
    }
}

@Preview
@Composable
private fun InitialFlow2Preview() {
    RunningAvaterTheme {
        InitialFlow2Screen(rememberNavController())
    }
}
