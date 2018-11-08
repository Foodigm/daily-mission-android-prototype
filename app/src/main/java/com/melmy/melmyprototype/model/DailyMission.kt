package com.melmy.melmyprototype.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "daily_missions")
data class DailyMission(
        @PrimaryKey
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "type") val type: MissionType,
        @ColumnInfo(name = "acc_minute") val accMinuteDaily: Int,
        @ColumnInfo(name = "date") val date: Calendar,
        @ColumnInfo(name = "acc_count") val accCountDaily: Int
)