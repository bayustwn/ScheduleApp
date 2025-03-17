package com.sheduleapp.domain.model

data class Schedule(
    val id: Int,
    val title: String,
    val desc: String,
    val date: String,
    val time: String
)