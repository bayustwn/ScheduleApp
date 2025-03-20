package com.sheduleapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Schedule(
    val id: Int?=null,
    val title: String,
    val desc: String,
    val day: String,
    val time: String
): Parcelable