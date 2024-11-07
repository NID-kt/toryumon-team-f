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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.runningavater.R
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.initialFlow.components.NextButton
import com.example.runningavater.ui.theme.RunningAvaterTheme

@Composable
fun InitialFlow1Screen(navController: NavHostController) {
    InitialFlowBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(0.dp, 50.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            )  {
                Text(
                    text = "てくてくダイエットへ",
                    color = Color.Black,
                    fontSize = 36.sp,
                )
                Text(
                    text = "ようこそ！",
                    color = Color.Black,
                    fontSize = 36.sp,
                )
                Image(
                    painter = painterResource(id = R.drawable.app_icon),
                    contentDescription = "App_Icon",
                    modifier = Modifier
                        .offset((0.dp), (50.dp))
                        .size(300.dp),
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(20.dp, 0.dp, 20.dp, 20.dp)
            ) {
                NextButton(
                    navController = rememberNavController(),
                    nextDestination = "InitialFlow/2",
                )
            }
        }
    }
}




@Preview
@Composable
private fun InitialFlow1Preview() {
    RunningAvaterTheme {
        InitialFlow1Screen(rememberNavController())
    }
}
