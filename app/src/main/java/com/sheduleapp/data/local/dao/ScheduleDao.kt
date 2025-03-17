package com.sheduleapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sheduleapp.data.local.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule WHERE date = :today AND time  >= :currentTime ORDER BY time ASC LIMIT 1")
    fun getNearestSchedule(today:String,currentTime: String): Flow<ScheduleEntity>

    @Query("SELECT * FROM schedule ORDER BY date ASC, time ASC")
    fun getAllSchedule(): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSchedule(schedule: ScheduleEntity)

    @Delete
    suspend fun deleteSchedule(schedule: ScheduleEntity)
}