package com.example.runningavater.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import bearName
import dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class NotifyMorningWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {

        // Do the work here--in this case, upload the images.
        notifyMorning(applicationContext)


        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
    companion object {
        fun schedule(context: Context) {
            val now = LocalDateTime.now()
            val nextRun = now.withHour(7).withMinute(0).withSecond(0).withNano(0)
                .let { if (it.isBefore(now)) it.plusDays(1) else it }

            val delay = Duration.between(now, nextRun)
            val notifyMorningRequest =
                PeriodicWorkRequestBuilder<NotifyMorningWorker>(1, TimeUnit.DAYS)
                    // Additional configuration
                    .setInitialDelay(delay)
                    .build()
            WorkManager
                .getInstance(context)
                .enqueue(notifyMorningRequest)
        }
    }
}

class NotifyNightWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {

        val bearNameString = applicationContext.dataStore.data.map { preferences ->
            preferences[bearName] ?: ""
        }.first()
        // Do the work here--in this case, upload the images.
        notifyNight(applicationContext, bearNameString)


        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
    companion object {
        fun schedule(context: Context) {
            val now = LocalDateTime.now()
            val nextRun = now.withHour(22).withMinute(0).withSecond(0).withNano(0)
                .let { if (it.isBefore(now)) it.plusDays(1) else it }

            val delay = Duration.between(now, nextRun)
            val notifyNightRequest =
                PeriodicWorkRequestBuilder<NotifyNightWorker>(1, TimeUnit.DAYS)
                    // Additional configuration
                    .setInitialDelay(delay)
                    .build()
            WorkManager
                .getInstance(context)
                .enqueue(notifyNightRequest)
        }
    }
}

class NotifyRandomWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    override fun doWork(): Result {


        if(Random.nextInt(1,24) <= 2) {
            notifyRandom(applicationContext)
        }

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
    companion object {
        fun schedule(context: Context) {
            val now = LocalDateTime.now()
            val nextRun = now.withHour(Random.nextInt(7, 23)).withMinute(0).withSecond(0).withNano(0)
                .let { if (it.isBefore(now)) it.plusDays(1) else it }

            val delay = Duration.between(now, nextRun)
            val notifyRandomRequest =
                PeriodicWorkRequestBuilder<NotifyRandomWorker>(1, TimeUnit.HOURS)
                    // Additional configuration
                    .setInitialDelay(delay)
                    .build()
            WorkManager
                .getInstance(context)
                .enqueue(notifyRandomRequest)
        }
    }
}
