package com.melmy.melmyprototype.missionlist

import com.melmy.melmyprototype.data.Mission

interface MissionItemCallback {
    fun deleteMission(mission: Mission)

    fun editMission(missionId: Long)

    fun openMissionDetail(missionId: Long)
}