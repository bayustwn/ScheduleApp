package com.sheduleapp.di

import com.sheduleapp.data.local.dao.ScheduleDao
import com.sheduleapp.data.repository.RepositoryImpl
import com.sheduleapp.domain.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(dao: ScheduleDao): ScheduleRepository{
        return RepositoryImpl(dao)
    }

}