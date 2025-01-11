package com.example.runningavater
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.runningavater.notification.createNotificationChannels
import com.example.runningavater.ui.theme.RunningAvaterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // /今は一旦ここに置いているがクマの名前を登録してから実行する
        createNotificationChannels(context = this, bearname = "権左衛門")
        setContent {
            RunningAvaterTheme {
                // A surface container using the 'background' color from the theme
                // 背景色をテーマから取得
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MyAppNavHost()
                }
            }
        }
    }
}
