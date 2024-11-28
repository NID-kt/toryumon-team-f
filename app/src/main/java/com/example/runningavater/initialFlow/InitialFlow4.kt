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
fun InitialFlow4Screen(navController: NavController) {
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
                    text = "一緒にアバターも",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "痩せるよ！",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "（ただし歩かない期間が長いと",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier =
                        Modifier
                            .offset((40.dp), (0.dp)),
                )
                Text(
                    text = "逆に太ってしまうことも...",
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier =
                        Modifier
                            .offset((100.dp), (0.dp)),
                )
                Image(
                    painter = painterResource(id = R.drawable.initialflow4img),
                    contentDescription = "クマちゃんの変化説明",
                    modifier =
                        Modifier
                            .offset((0.dp), (50.dp))
                            .size(375.dp),
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
                    nextDestination = "initialFlow/5",
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
private fun InitialFlow4Preview()  {
    RunningAvaterTheme {
        InitialFlow4Screen(rememberNavController())
    }
}
