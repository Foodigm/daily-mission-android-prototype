package com.melmy.melmyprototype.utils

import com.melmy.melmyprototype.model.Mission
import com.melmy.melmyprototype.model.MissionType

fun Mission.getAchievePercent() =
        if (this.type == MissionType.COUNT) {
            ((this.accCountTotal.toFloat()) / this.goalCountTotal * 100).toInt()
        } else {
            ((this.accMinuteTotal.toFloat()) / this.goalMinuteTotal * 100).toInt()
        }

