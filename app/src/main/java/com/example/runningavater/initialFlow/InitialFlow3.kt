package com.example.runningavater.initialFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.R
import com.example.runningavater.initialFlow.components.BackButton
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.initialFlow.components.NextButton
import com.example.runningavater.ui.theme.RunningAvaterTheme

@Composable
fun InitialFlow3Screen(navController: NavController) {
    InitialFlowBackground {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
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
                    text = "あなたが歩くと...",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(980.dp),
                ) {
                    Image(
                        painter =
                            painterResource
                            (id = R.drawable.initialflow3imghuman),
                        contentDescription = "くまちゃんと散歩している人",
                        modifier =
                            Modifier
                                .offset((0.dp), (100.dp))
                                .size(400.dp),
                    )
                }
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
            ) {
                Image(
                    painter =
                        painterResource
                        (id = R.drawable.initialflow3imgbear),
                    contentDescription = "人と歩いているくまちゃん",
                    modifier =
                        Modifier
                            .size(200.dp)
                            .padding(0.dp, 0.dp, 0.dp, 0.dp),
                )
            }
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp, 0.dp, 20.dp, 90.dp),
            ) {
                NextButton(
                    navController = navController,
                    nextDestination = "InitialFlow/4",
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
private fun InitialFlow3Preview() {
    RunningAvaterTheme {
        InitialFlow3Screen(rememberNavController())
    }
}
