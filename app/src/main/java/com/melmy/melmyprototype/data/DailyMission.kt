package com.melmy.melmyprototype.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(
        tableName = "daily_missions",
        foreignKeys = [ForeignKey(entity = Mission::class, parentColumns = ["id"], childColumns = ["mission_id"], onDelete = CASCADE)],
        indices = [Index(value = ["mission_id"], name = "id")]
)
data class DailyMission(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long = 0,
        @ColumnInfo(name = "mission_id") val missionId: Long,
        @ColumnInfo(name = "create_date") val date: Calendar = Calendar.getInstance(),
        @ColumnInfo(name = "acc_minute") val accMinuteDaily: Int = 0,
        @ColumnInfo(name = "acc_count") val accCountDaily: Int = 0
)