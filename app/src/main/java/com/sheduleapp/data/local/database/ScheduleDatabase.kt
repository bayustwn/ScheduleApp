package com.sheduleapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sheduleapp.data.local.dao.ScheduleDao
import com.sheduleapp.data.local.entity.ScheduleEntity

@Database(entities = [ScheduleEntity::class], version = 1)
abstract class ScheduleDatabase: RoomDatabase() {
    abstract fun scheduleDao() : ScheduleDao
}