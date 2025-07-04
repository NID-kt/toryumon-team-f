package org.nidkt.tekuteku.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_data")
data class StepDate(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val day: Long,
)
