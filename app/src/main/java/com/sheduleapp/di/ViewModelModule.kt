package com.sheduleapp.di

import android.content.Context
import com.sheduleapp.domain.repository.ScheduleRepository
import com.sheduleapp.domain.usecase.ScheduleUseCases
import com.sheduleapp.presentation.viewmodel.ScheduleViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideViewmodel(scheduleUseCases: ScheduleUseCases,@ApplicationContext context: Context): ScheduleViewModel {
        return ScheduleViewModel(scheduleUseCases,context)
    }

}