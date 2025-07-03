package org.nidkt.tekuteku.initialFlow

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.nidkt.tekuteku.R
import org.nidkt.tekuteku.initialFlow.components.BackButton
import org.nidkt.tekuteku.initialFlow.components.InitialFlowBackground
import org.nidkt.tekuteku.initialFlow.components.NextButton
import org.nidkt.tekuteku.ui.theme.RunningAvaterTheme

@Composable
fun InitialFlow9Screen(navController: NavController) {
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
                    text = "通知をオンにすると",
                    color = Color.Black,
                    fontSize = 32.sp,
                )
                Text(
                    text = "sampleの変化や目標の",
                    color = Color.Black,
                    fontSize = 32.sp,
                )
                Text(
                    text = "達成がすぐにわかるよ！",
                    color = Color.Black,
                    fontSize = 32.sp,
                )

                Image(
                    painter = painterResource(id = R.drawable.initialflow9img),
                    contentDescription = "くま見守ろう",
                    modifier =
                        Modifier
                            .offset((0.dp), (50.dp))
                            .size(400.dp),
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
                    nextDestination = "InitialFlow/10",
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
private fun InitialFlow9Preview() {
    RunningAvaterTheme {
        InitialFlow9Screen(rememberNavController())
    }
}
