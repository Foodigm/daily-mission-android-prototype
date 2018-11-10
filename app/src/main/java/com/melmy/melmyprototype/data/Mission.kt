package com.melmy.melmyprototype.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melmy.melmyprototype.utils.DayOfWeekSet
import java.util.*

@Entity(tableName = "missions")
data class Mission(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long = 0,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false,
        @ColumnInfo(name = "days") val days: DayOfWeekSet,
        @ColumnInfo(name = "completed_date") val completedDate: Calendar = Calendar.getInstance(),
        @ColumnInfo(name = "type") val type: MissionType,
        @ColumnInfo(name = "goal_minute_total") val goalMinuteTotal: Int = 0,
        @ColumnInfo(name = "goal_minute_daily") val goalMinuteDaily: Int = 0,
        @ColumnInfo(name = "acc_minute_total") val accMinuteTotal: Int = 0,
        @ColumnInfo(name = "goal_count_total") val goalCountTotal: Int = 0,
        @ColumnInfo(name = "goal_count_daily") val goalCountDaily: Int = 0,
        @ColumnInfo(name = "acc_count_total") val accCountTotal: Int = 0,
        @ColumnInfo(name = "acc_upper_fulfill_rate") val accUpperFulfillRate: Int = 0,
        @ColumnInfo(name = "acc_lower_fulfill_rate") val accLowerFulfillRate: Int = 0
)

