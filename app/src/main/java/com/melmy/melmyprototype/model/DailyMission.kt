package com.melmy.melmyprototype.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(
        tableName = "daily_missions",
        foreignKeys = [ForeignKey(entity = Mission::class, parentColumns = ["id"], childColumns = ["mission_id"], onDelete = CASCADE)]
)
data class DailyMission(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "mission_id") val missionId: Long,
        @ColumnInfo(name = "type") val type: MissionType,
        @ColumnInfo(name = "acc_minute") val accMinuteDaily: Int,
        @ColumnInfo(name = "date") val date: Calendar,
        @ColumnInfo(name = "acc_count") val accCountDaily: Int
)