package com.example.runningavater.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "step_data")
data class StepDate(
   @PrimaryKey(autoGenerate = true) val id:Long,
    val day:Long
) {
}
