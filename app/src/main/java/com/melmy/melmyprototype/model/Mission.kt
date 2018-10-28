package com.melmy.melmyprototype.model

import java.time.DayOfWeek

data class Mission (
    val id: Long,
    val title: String,
    val type: MissionType,
    val goalMinuteTotal: Int,
    val goalCountTotal: Int,
    val accMinuteTotal: Int,
    val accCountTotal: Int,
    val goalMinuteDaily: Int,
    val goalCountDaily: Int,
    val accMinuteDaily: Int,
    val accCountDaily: Int,
    val isCompleted: Boolean,
    val days: List<DayOfWeek>
)

enum class MissionType {
    TIME, COUNT
}
