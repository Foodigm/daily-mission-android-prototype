package com.melmy.melmyprototype.utils

import java.util.*

class DayOfWeekSet {
    var monday = false
    var tuesday = false
    var wednesday = false
    var thursday = false
    var friday = false
    var saturday = false
    var sunday = false

    constructor()
    constructor(bitmask: Int) {
        if (((bitmask shr 0) and 1) == 1) {
            monday = true
        }
        if (((bitmask shr 1) and 1) == 1) {
            tuesday = true
        }
        if (((bitmask shr 2) and 1) == 1) {
            wednesday = true
        }
        if (((bitmask shr 3) and 1) == 1) {
            thursday = true
        }
        if (((bitmask shr 4) and 1) == 1) {
            friday = true
        }
        if (((bitmask shr 5) and 1) == 1) {
            saturday = true
        }
        if (((bitmask shr 6) and 1) == 1) {
            sunday = true
        }
    }

    fun toInt(): Int {
        var bitmask = 0
        if (monday) bitmask = bitmask or (1 shl 0)
        if (tuesday) bitmask = bitmask or (1 shl 1)
        if (wednesday) bitmask = bitmask or (1 shl 2)
        if (thursday) bitmask = bitmask or (1 shl 3)
        if (friday) bitmask = bitmask or (1 shl 4)
        if (saturday) bitmask = bitmask or (1 shl 5)
        if (sunday) bitmask = bitmask or (1 shl 6)

        return bitmask
    }

    fun isTodayOn(): Boolean {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> monday == true
            Calendar.TUESDAY -> tuesday == true
            Calendar.WEDNESDAY -> wednesday == true
            Calendar.THURSDAY -> thursday == true
            Calendar.FRIDAY -> friday == true
            Calendar.SATURDAY -> saturday == true
            else -> sunday == true
        }
    }

    fun toggleMonday() {
        monday = !monday
    }

    fun toggleTuesday() {
        tuesday = !tuesday
    }

    fun toggleWednesday() {
        wednesday = !wednesday
    }

    fun toggleThursday() {
        thursday = !thursday
    }

    fun toggleFriday() {
        friday = !friday
    }

    fun toggleSaturday() {
        saturday = !saturday
    }

    fun toggleSunday() {
        sunday = !sunday
    }
}