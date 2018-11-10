package com.melmy.melmyprototype.data

import androidx.room.TypeConverter
import com.melmy.melmyprototype.utils.DayOfWeekSet
import java.util.*

class Converters {
    @TypeConverter
    fun missionTypeToString(missionType: MissionType): String {
        return when (missionType) {
            MissionType.COUNT -> "count"
            MissionType.TIME -> "time"
        }
    }

    @TypeConverter
    fun stringToMissionType(missionType: String): MissionType {
        return when (missionType) {
            "count" -> MissionType.COUNT
            "time" -> MissionType.TIME
            else -> MissionType.TIME
        }
    }

    @TypeConverter
    fun dayOfWeekSetToBitmask(days: DayOfWeekSet): Int {
        return days.toInt()
    }

    @TypeConverter
    fun bitmaskToDayOfWeekSet(bitmask: Int): DayOfWeekSet {
        return DayOfWeekSet(bitmask)
    }

    @TypeConverter
    fun calendarToTimestamp(calendar: Calendar) = calendar.timeInMillis

    @TypeConverter
    fun timestampToCalendar(value: Long): Calendar =
            Calendar.getInstance().apply { timeInMillis = value }
}