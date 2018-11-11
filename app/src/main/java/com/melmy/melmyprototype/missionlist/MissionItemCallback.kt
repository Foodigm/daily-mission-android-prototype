package com.melmy.melmyprototype.missionlist

interface MissionItemCallback {
    fun deleteMission(missionId: Long)

    fun editMission(missionId: Long)

    fun openMissionDetail(missionId: Long)
}