package com.example.runningavater.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StepDate::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stepDateDao(): StepDateDao
}
