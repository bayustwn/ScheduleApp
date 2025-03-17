package com.sheduleapp.domain.usecase

import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.domain.repository.ScheduleRepository

class DeleteScheduleUseCase (private val repository: ScheduleRepository) {

    suspend operator fun invoke(schedule: Schedule){
        repository.deleteSchedule(schedule)
    }

}