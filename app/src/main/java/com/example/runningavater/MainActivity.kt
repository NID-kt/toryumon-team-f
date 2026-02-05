package com.example.runningavater

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.example.runningavater.notification.createNotificationChannels
import com.example.runningavater.ui.theme.RunningAvaterTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // /今は一旦ここに置いているがクマの名前を登録してから実行する
        createNotificationChannels(context = this, bearname = "権左衛門")

        setContent {
            RunningAvaterTheme {
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
