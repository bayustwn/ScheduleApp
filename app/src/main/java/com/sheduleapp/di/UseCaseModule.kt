package com.sheduleapp.di

import com.sheduleapp.domain.repository.ScheduleRepository
import com.sheduleapp.domain.usecase.AddScheduleUseCase
import com.sheduleapp.domain.usecase.DeleteScheduleUseCase
import com.sheduleapp.domain.usecase.GetAllScheduleUseCase
import com.sheduleapp.domain.usecase.GetNearestSchedule
import com.sheduleapp.domain.usecase.ScheduleUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCase(repository: ScheduleRepository): ScheduleUseCases{
        return ScheduleUseCases(
            getAllSchedule = GetAllScheduleUseCase(repository),
            deleteSchedule = DeleteScheduleUseCase(repository),
            addSchedule = AddScheduleUseCase(repository),
            getNearestSchedule = GetNearestSchedule(repository)
        )
    }

}