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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.R
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.initialFlow.components.NextButton
import com.example.runningavater.ui.theme.RunningAvaterTheme

@Composable
fun InitialFlow10Screen(navController: NavHostController) {
    InitialFlowBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "さっそくダイエットを\n始めよう！",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 46.3.sp,
                )
            }
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.initial10img),
                    contentDescription = "big bear",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.78f)
                            .zIndex(-1f),
                    contentScale = ContentScale.Crop,
                )
//
            }
            Column(
                modifier =
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(0.dp, 0.dp, 0.dp, 20.dp),
            ) {
                NextButton(
                    navController = navController,
                    nextDestination = "home",
                    text = "始める",
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun InitialFlow10Preview() {
    RunningAvaterTheme {
        InitialFlow10Screen(rememberNavController())
    }
}
