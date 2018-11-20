package com.melmy.melmyprototype.utils

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

fun secondsToStringFormat(timeSeconds: Int): String {
    var hour = timeSeconds / 3600
    var minute = (timeSeconds % 3600) / 60
    var second = (timeSeconds % 60)
    var str1 = hour.toString()
    var str2 = minute.toString()
    var str3 = second.toString()
    if (str1.length < 2) str1 = "0$str1"
    if (str2.length < 2) str2 = "0$str2"
    if (str3.length < 2) str3 = "0$str3"
    return "$str1:$str2:$str3"
}