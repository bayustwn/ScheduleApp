package com.sheduleapp.di

import android.content.Context
import androidx.room.Room
import com.sheduleapp.data.local.dao.ScheduleDao
import com.sheduleapp.data.local.database.ScheduleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ScheduleDatabase {
        return Room.databaseBuilder(context, ScheduleDatabase::class.java, "schedule_db").build()
    }

    @Provides
    fun provideDao(db: ScheduleDatabase) : ScheduleDao {
        return db.scheduleDao()
    }

}