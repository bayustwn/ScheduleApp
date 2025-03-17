package com.sheduleapp.presentation.state

import com.sheduleapp.domain.model.Schedule

sealed class ScheduleUIState {
    object Loading : ScheduleUIState()
    data class Success(val schedules: List<Schedule>) : ScheduleUIState()
    data class Error(val message: String) : ScheduleUIState()
    object Empty : ScheduleUIState()
}