package com.example.runningavater

import afterLevelKey
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import beforeLevelKey
import currentLevelKey
import dataStore
import kotlinx.coroutines.flow.first
import targetSteps
import java.util.concurrent.TimeUnit

class AggregateSteps(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {

        val app = applicationContext as MainApplication
        val success = app.db.stepDateDao().getStepGoalSuccessDaysCount(app.dataStore.data.first()[targetSteps]!!)
        val nowLevelcount = aggregateDays(app.dataStore.data.first()[currentLevelKey] ?: 2)
        val beforeLevel = app.dataStore.data.first()[beforeLevelKey] ?: 2
        if (nowLevelcount / 2 <= success) {
            app.dataStore.edit {
                it[currentLevelKey] = (it[currentLevelKey] ?: 2).plus(1)
            }
        } else {
            app.dataStore.edit {
                it[currentLevelKey] = (it[currentLevelKey] ?: 2).minus(1)
            }
        }
        val afterLevel = app.dataStore.data.first()[currentLevelKey] ?: 2
        app.dataStore.edit {
            it[beforeLevelKey] = beforeLevel
            it[afterLevelKey] = afterLevel
        }
        val aggregateSteps = OneTimeWorkRequestBuilder<AggregateSteps>()
            .setInitialDelay(aggregateDays(afterLevel).toLong(), TimeUnit.DAYS)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(aggregateSteps)

        return Result.success()


    }
}

fun aggregateDays(level: Int) = when (level) {
    1 -> 3
    2 -> 6
    3 -> 9
    else -> 12

}
