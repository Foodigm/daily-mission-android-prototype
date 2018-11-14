package com.melmy.melmyprototype.utils

import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionType
import java.util.*

fun Mission.getAchievePercent() =
        if (this.type == MissionType.COUNT) {
            ((this.accCountTotal.toFloat()) / this.goalCountTotal * 100).toInt()
        } else {
            ((this.accMinuteTotal.toFloat()) / this.goalMinuteTotal * 100).toInt()
        }

fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
    return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
            calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)
}