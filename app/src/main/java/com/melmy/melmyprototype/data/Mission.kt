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
        @ColumnInfo(name = "created_date") val createdDate: Calendar = Calendar.getInstance(),
        @ColumnInfo(name = "type") val type: MissionType,
        @ColumnInfo(name = "goal_minute_total") val goalMinuteTotal: Int = 0,
        @ColumnInfo(name = "goal_minute_daily") val goalMinuteDaily: Int = 0,
        @ColumnInfo(name = "acc_minute_total") val accMinuteTotal: Int = 0,
        @ColumnInfo(name = "goal_count_total") val goalCountTotal: Int = 0,
        @ColumnInfo(name = "goal_count_daily") val goalCountDaily: Int = 0,
        @ColumnInfo(name = "acc_count_total") val accCountTotal: Int = 0,
        @ColumnInfo(name = "acc_upper_fulfill_rate") val accUpperFulfillRate: Int = 0,
        @ColumnInfo(name = "acc_lower_fulfill_rate") val accLowerFulfillRate: Int = 0,
        @ColumnInfo(name = "acc_minute_daily") val accMinuteDaily: Int = 0,
        @ColumnInfo(name = "acc_count_daily") var accCountDaily: Int = 0
) {
    fun getTotalAchievePercent() =
            if (this.type == MissionType.COUNT) {
                ((this.accCountTotal.toFloat()) / this.goalCountTotal * 100).toInt()
            } else {
                ((this.accMinuteTotal.toFloat()) / this.goalMinuteTotal * 100).toInt()
            }

    fun getDailyAchievePercent() =
            if (this.type == MissionType.COUNT) {
                ((this.accCountDaily.toFloat()) / this.goalCountDaily * 100).toInt()
            } else {
                ((this.accMinuteDaily.toFloat()) / this.goalMinuteDaily * 100).toInt()
            }

    fun isCompletedToday() =
            this.accCountDaily == this.goalCountDaily
}

