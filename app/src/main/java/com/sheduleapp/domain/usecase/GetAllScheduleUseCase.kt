package com.sheduleapp.domain.usecase

import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow

class GetAllScheduleUseCase(private val repository: ScheduleRepository) {
    operator fun invoke(): Flow<List<Schedule>>{
        return repository.getAllSchedule()
    }
}