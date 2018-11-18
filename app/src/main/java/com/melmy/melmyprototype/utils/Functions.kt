package com.melmy.melmyprototype.utils

import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionType
import java.util.*

fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}


fun isToday(calendar: Calendar): Boolean {
    val today = Calendar.getInstance()
    return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
            calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
}
