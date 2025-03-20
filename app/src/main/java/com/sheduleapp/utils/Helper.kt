package com.sheduleapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Helper {
    fun getDateToday(): String {
        return SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
    }

    fun getCurrentTime(): String {
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
    }

}