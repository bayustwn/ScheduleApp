package com.sheduleapp.domain.repository

import com.sheduleapp.domain.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getNearestSchedule(): Flow<Schedule?>
    fun getAllSchedule() : Flow<List<Schedule>>
    suspend fun addSchedule(schedule: Schedule)
    suspend fun deleteSchedule(schedule: Schedule)
}