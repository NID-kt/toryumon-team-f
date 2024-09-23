package com.example.runningavater

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.filament.Colors

@Composable
fun NextButton(navController: NavHostController, nextDestination: String){
    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp, 100.dp),
    ){
        Button(
            onClick = {navController.navigate(nextDestination)},
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.NuclearMango)
            )
        ) {
                    Text(text = "次へ")
//                        modifier = Modifier
//                            .fillMaxSize(),
        }
    }
}


@Composable
fun InitialFlow2(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.SungYellow))
            .padding(0.dp, 50.dp, 0.dp, 0.dp)
        )
    {
        Text(
            text = "可愛いアバターと一緒に",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp
        )
        Text(
            text = "楽しくダイエット♪",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 32.sp
        )
        NextButton(navController = navController, nextDestination = "Initial5")
    }
}


    @Composable
    fun InitialFlow5(navController: NavHostController) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.SungYellow))
                .padding(0.dp, 50.dp, 0.dp, 0.dp)
        ) {
            Text(
                text = "くまに名前をつけて",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
            Text(
                text = "あげましょう",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )
            NextButton(navController = navController, nextDestination = "Initial2")
        }
    }

//fun InitialFlow 何画面目か
