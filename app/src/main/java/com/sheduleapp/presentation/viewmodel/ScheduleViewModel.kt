package com.sheduleapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sheduleapp.R
import com.sheduleapp.domain.model.Schedule
import com.sheduleapp.domain.usecase.ScheduleUseCases
import com.sheduleapp.presentation.state.ScheduleUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleUseCases: ScheduleUseCases,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val nearestSchedule: StateFlow<ScheduleUIState> = scheduleUseCases.getNearestSchedule()
        .map { schedule ->
            if (schedule != null) {
                ScheduleUIState.Success(listOf(schedule))
            } else {
                ScheduleUIState.Empty
            }
        }
        .catch { exception ->
            emit(ScheduleUIState.Error(exception.message ?: context.getString(R.string.error_load)))
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ScheduleUIState.Loading
        )

    val scheduleState: StateFlow<ScheduleUIState> = scheduleUseCases.getAllSchedule()
        .map { schedules ->
            if (schedules.isNotEmpty()) {
                ScheduleUIState.Success(schedules)
            } else {
                ScheduleUIState.Empty
            }
        }
        .catch { exception ->
            emit(ScheduleUIState.Error(exception.message ?: context.getString(R.string.error_load)))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScheduleUIState.Loading
        )

    fun addSchedule(schedule: Schedule){
        viewModelScope.launch {
            scheduleUseCases.addSchedule(schedule)
        }
    }

    fun deleteSchedule(schedule: Schedule){
        viewModelScope.launch {
            scheduleUseCases.deleteSchedule(schedule)
        }
    }
}