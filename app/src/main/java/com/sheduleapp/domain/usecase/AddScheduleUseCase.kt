package com.sheduleapp.domain.usecase

import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.domain.repository.ScheduleRepository

class AddScheduleUseCase(private val repository: ScheduleRepository) {

    suspend operator fun invoke(schedule: Schedule){
        repository.addSchedule(schedule)
    }

}