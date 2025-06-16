package com.example.runningavater

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.runningavater.notification.createNotificationChannels
import com.example.runningavater.ui.theme.RunningAvaterTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            //テストデータ追加
            val app = applicationContext as MainApplication
            app.db.stepDateDao().deleteAll()
//            repeat(1200){
//                app.db.stepDateDao().insertAll(StepDate(id = 0,  1743433200))
//            }
//            repeat(900){
//                app.db.stepDateDao().insertAll(StepDate(id = 0,  1743519600))
//            }
//            repeat(1000){
//                app.db.stepDateDao().insertAll(StepDate(id = 0,  1743606000))
//            }
//            val test=app.db.stepDateDao().getStepGoalSuccessDaysCount(targetSteps = 1000)
//            println(test)


        }

       
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
