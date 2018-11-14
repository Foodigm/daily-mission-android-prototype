package com.melmy.melmyprototype.data

import com.melmy.melmyprototype.utils.runOnIoThread

class MissionRepository private constructor(
        private val missionDao: MissionDao,
        private val dailyMissionDao: DailyMissionDao
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

    fun getMissionsObservable() =
            missionDao.getAllMissionsObservable()

    fun insertDailyMissions(mission: List<DailyMission>) {
        runOnIoThread {
            dailyMissionDao.insertMissions(mission)
        }
    }

    fun removeDailyMission(mission: DailyMission) {
        runOnIoThread {
            dailyMissionDao.deleteMission(mission)
        }
    }

    fun getMission(missionId: Long, callback: MissionDataSource.GetMissionCallback) {
        runOnIoThread {
            val mission = missionDao.getMission(missionId)
            if (mission != null) {
                callback.onMissionLoaded(mission)
            } else {
                callback.onDataNotAvailable()
            }
        }
    }

    fun updateAccTimeOrCount(dailyMissions: List<DailyMissionJoined>) {
        runOnIoThread {
            missionDao.accumulateDailyWorkAndDelete(dailyMissions)
        }
    }

    fun getDailyMissions() =
            dailyMissionDao.getAllDailyMissionsJoined()

    companion object {

        @Volatile
        var instance: MissionRepository? = null

        fun getInstance(missionDao: MissionDao, dailyMissionDao: DailyMissionDao) =
                instance ?: synchronized(this) {
                    instance
                            ?: MissionRepository(missionDao, dailyMissionDao).also { instance = it }
                }
    }
}