package com.sheduleapp.data.mapper

import com.sheduleapp.data.local.entity.ScheduleEntity
import com.sheduleapp.domain.model.Schedule

object Mapper {

    fun ScheduleEntity.toDomain(): Schedule {
        return Schedule(id = id, title = title, desc = desc, dateTime = dateTime)
    }

    fun Schedule.toEntity(): ScheduleEntity {
        return ScheduleEntity(id = id, title = title, desc = desc, dateTime = dateTime)
    }

}