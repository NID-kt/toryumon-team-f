package org.nidkt.tekuteku

import android.app.Application
import androidx.room.Room
import org.nidkt.tekuteku.db.AppDatabase

class MainApplication : Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database-name",
        ).build()
    }
}
