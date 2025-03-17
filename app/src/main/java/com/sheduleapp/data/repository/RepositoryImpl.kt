package com.sheduleapp.data.repository

import com.sheduleapp.data.local.dao.ScheduleDao
import com.sheduleapp.data.mapper.Mapper.toDomain
import com.sheduleapp.data.mapper.Mapper.toEntity
import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.domain.repository.ScheduleRepository
import com.sheduleapp.utils.Helper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(private val dao: ScheduleDao) : ScheduleRepository {
    override fun getNearestSchedule(): Flow<Schedule?> {
        val today = Helper.getDateToday()
        val currentTime = Helper.getCurrentTime()

        return dao.getNearestSchedule(today, currentTime).map { it.toDomain() }
    }

    override fun getAllSchedule(): Flow<List<Schedule>> {
        return dao.getAllSchedule().map { it.map { it.toDomain() } }
    }

    override suspend fun addSchedule(schedule: Schedule)  {
        dao.addSchedule(schedule.toEntity())
    }

    override suspend fun deleteSchedule(schedule: Schedule) {
        dao.deleteSchedule(schedule.toEntity())
    }

}