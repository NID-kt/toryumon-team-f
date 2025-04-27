package com.example.runningavater.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StepDateDao {
    @Insert
    fun insertAll(vararg stepDate: StepDate)
    @Query("DELETE FROM step_data")
    fun deleteAll()
    @Query("SELECT COUNT(*) From ("
    +"SELECT COUNT(*) FROM step_data GROUP BY strftime('%Y-%m-%d',step_data.day,'unixepoch') HAVING COUNT(*) >= :targetSteps)")
    fun getStepGoalSuccessDaysCount(targetSteps: Int):Int
    @Query("SELECT COUNT(*) from step_data WHERE day >= :start and day <= :end")
    fun getTotalWalk(
        start: Long,
        end: Long,
    ): Int
}
