package com.melmy.melmyprototype.data

import com.melmy.melmyprototype.utils.runOnIoThread

class MissionRepository private constructor(
        private val db: AppDatabase
) {
    private val missionDao = db.missionDao()
    private val dailyMissionDao = db.dailyMissionDao()

    fun createMission(mission: Mission) {
        runOnIoThread {
            val dailyMission = DailyMission(missionId = mission.id)
            db.runInTransaction {
                /* TODO  mission 을 먼저 넣고나서 dailyMission 을 넣어야 Foreign Key 관련하여 에러가 안나기때문에 여기 수정해야됨*/
                missionDao.insertMission(mission)
                dailyMissionDao.insertMission(dailyMission)
            }
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

    fun insertDailyMissions(missions: List<DailyMission>) {
        runOnIoThread {
            dailyMissionDao.insertMissions(missions)
        }
    }

    fun insertDailyMission(mission: DailyMission) {
        runOnIoThread {
            dailyMissionDao.insertMission(mission)
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

        fun getInstance(db: AppDatabase) =
                instance ?: synchronized(this) {
                    instance
                            ?: MissionRepository(db).also { instance = it }
                }
    }
}