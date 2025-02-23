package com.example.runningavater.db

import androidx.compose.foundation.interaction.DragInteraction
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StepDateDao {
    @Insert
    fun  insertAll(vararg stepDate: StepDate)
    @Query("SELECT COUNT(*) from step_data WHERE day >= :start and day <= :end")
    fun getTotalWalk(start:Long,end:Long):Int
}
