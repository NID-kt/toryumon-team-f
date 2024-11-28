package com.example.runningavater.initialFlow

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
fun InitialFlow6Screen(
    bearName: String,
    navController: NavHostController,
) {
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
                    text = "この子があなたの",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "相棒となる",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = bearName,
                    color = Color.Black,
                    fontSize = 96.sp,
                    lineHeight = 80.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "だ！",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
                Image(
                    painter = painterResource(id = R.drawable.initialflow56),
                    contentDescription = "initial6img",
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(0.dp, 24.dp, 0.dp, 24.dp)
                            .size(300.dp),
                )
                NextButton(
                    navController = navController,
                    nextDestination = "initialFlow/8",
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp),
                )
                BackButton(
                    navController = navController,
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun InitialFlow6Preview() {
    RunningAvaterTheme {
        InitialFlow6Screen(
            bearName = "デイブ・ヤ・lllllllll",
            rememberNavController(),
        )
    }
}
