package com.melmy.melmyprototype.data

import com.melmy.melmyprototype.model.Mission
import com.melmy.melmyprototype.utils.runOnIoThread

class MissionRepository private constructor(
        private val missionDao: MissionDao
) {

    fun createMission(mission: Mission) {
        runOnIoThread {
            missionDao.insertMission(mission)
        }
    }

    fun removeMission(mission: Mission) {
        runOnIoThread {
            missionDao.deleteMission(mission)
        }
    }

    fun getMissions() =
            missionDao.getAllMissions()

    companion object {

        @Volatile
        var instance: MissionRepository? = null

        fun getInstance(missionDao: MissionDao) =
                instance ?: synchronized(this) {
                    instance
                            ?: MissionRepository(missionDao).also { instance = it }
                }
    }
}