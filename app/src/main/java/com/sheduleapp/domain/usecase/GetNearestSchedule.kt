package com.sheduleapp.domain.usecase

import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetNearestSchedule(private val repository: ScheduleRepository){

    operator fun invoke(): Flow<Schedule?>{
        return repository.getNearestSchedule()
    }

}