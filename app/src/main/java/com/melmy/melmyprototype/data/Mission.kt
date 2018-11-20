package com.melmy.melmyprototype.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.melmy.melmyprototype.utils.DayOfWeekSet
import java.util.*
import kotlin.math.min

@Entity(tableName = "missions")
data class Mission(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val id: Long = 0,
        @ColumnInfo(name = "title") val title: String,
        @ColumnInfo(name = "is_stopped") val isStopped: Boolean = false,
        @ColumnInfo(name = "days") val days: DayOfWeekSet,
        @ColumnInfo(name = "completed_date") val completedDate: Calendar = Calendar.getInstance(),
        @ColumnInfo(name = "created_date") val createdDate: Calendar = Calendar.getInstance(),
        @ColumnInfo(name = "type") val type: MissionType,
        @ColumnInfo(name = "goal_seconds_total") val goalSecondsTotal: Int = 0,
        @ColumnInfo(name = "goal_seconds_daily") val goalSecondsDaily: Int = 0,
        @ColumnInfo(name = "acc_seconds_total") val accSecondsTotal: Int = 0,
        @ColumnInfo(name = "goal_counts_total") val goalCountsTotal: Int = 0,
        @ColumnInfo(name = "goal_counts_daily") val goalCountsDaily: Int = 0,
        @ColumnInfo(name = "acc_counts_total") val accCountsTotal: Int = 0,
        @ColumnInfo(name = "acc_upper_fulfill_rate") val accUpperFulfillRate: Int = 0,
        @ColumnInfo(name = "acc_lower_fulfill_rate") val accLowerFulfillRate: Int = 0,
        @ColumnInfo(name = "acc_seconds_daily") val accSecondsDaily: Int = 0,
        @ColumnInfo(name = "acc_counts_daily") var accCountsDaily: Int = 0
) {
    fun getTodayGoalSecond() =
            min(this.goalSecondsTotal - this.accSecondsTotal, this.goalSecondsDaily)

    fun getTodayGoalCount() =
            min(this.goalCountsTotal - this.accCountsTotal, this.goalCountsDaily)

    fun getTotalAchievePercent() =
            when (this.type) {
                MissionType.COUNT ->
                    ((this.accCountsTotal.toFloat() + this.accCountsDaily.toFloat()) / this.goalCountsTotal * 100).toInt()
                else ->
                    ((this.accSecondsTotal.toFloat() + this.accSecondsDaily.toFloat()) / this.goalSecondsTotal * 100).toInt()
            }

    fun getDailyAchievePercent() =
            when (this.type) {
                MissionType.COUNT ->
                    ((this.accCountsDaily.toFloat()) / getTodayGoalCount() * 100).toInt()
                else ->
                    ((this.accSecondsDaily.toFloat()) / getTodayGoalSecond() * 100).toInt()
            }

    fun isCompletedToday() =
            when (this.type) {
                MissionType.COUNT -> this.accCountsDaily == getTodayGoalCount()
                else -> this.accSecondsDaily == getTodayGoalSecond()
            }

    fun isTotallyCompleted() =
            when (this.type) {
                MissionType.COUNT -> this.accCountsTotal == this.goalCountsTotal
                MissionType.TIME -> this.accSecondsTotal == this.goalSecondsTotal
            }
}

