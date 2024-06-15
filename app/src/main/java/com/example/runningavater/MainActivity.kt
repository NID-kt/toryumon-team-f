package com.example.runningavater


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.runningavater.ui.theme.RunningAvaterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RunningAvaterTheme {
                // A surface container using the 'background' color from the theme
                Surface( //背景色をテーマから取得
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyAppNavHost()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "ポリゴン $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RunningAvaterTheme {
        Greeting("今日は暑っかった初クーラつけました")
    }
}

@Composable
fun Greetingview(name: String,modifier: Modifier=Modifier){
    Text(text = "初心者すぎるけど頑張ります")
}
