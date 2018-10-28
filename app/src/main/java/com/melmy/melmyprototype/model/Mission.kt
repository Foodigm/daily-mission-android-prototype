package com.melmy.melmyprototype.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melmy.melmyprototype.utils.DayOfWeekSet

@Entity(tableName = "missions")
data class Mission(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "is_completed") val isCompleted: Boolean,
        @ColumnInfo(name = "days") val days: DayOfWeekSet,
        @ColumnInfo(name = "type") val type: MissionType,
        @ColumnInfo(name = "goal_minute_total") val goalMinuteTotal: Int,
        @ColumnInfo(name = "goal_minute_daily") val goalMinuteDaily: Int,
        @ColumnInfo(name = "acc_minute_total") val accMinuteTotal: Int,
        @ColumnInfo(name = "acc_minute_daily") val accMinuteDaily: Int,
        @ColumnInfo(name = "goal_count_total") val goalCountTotal: Int,
        @ColumnInfo(name = "goal_count_daily") val goalCountDaily: Int,
        @ColumnInfo(name = "acc_count_total") val accCountTotal: Int,
        @ColumnInfo(name = "acc_count_daily") val accCountDaily: Int
)

enum class MissionType {
    TIME, COUNT
}
