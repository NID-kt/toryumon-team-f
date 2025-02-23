package com.example.runningavater

import android.app.Application
import androidx.room.Room
import com.example.runningavater.db.AppDatabase

class MainApplication : Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-name",
        ).build()
    }
}
