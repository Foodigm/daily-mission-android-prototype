package com.melmy.melmyprototype.data

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class DailyMissionJoined(
        @Embedded
        val dailyMission: DailyMission,

//        @Embedded
//        val mission: Mission
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "type") val type: MissionType,
        @ColumnInfo(name = "goal_minute_daily") val goalMinuteDaily: Int = 0,
        @ColumnInfo(name = "goal_count_daily") val goalCountDaily: Int = 0
        )
