package com.sheduleapp.domain.usecase

data class ScheduleUseCases(
    val getNearestSchedule: GetNearestSchedule,
    val getAllSchedule: GetAllScheduleUseCase,
    val addSchedule: AddScheduleUseCase,
    val deleteSchedule: DeleteScheduleUseCase
)
