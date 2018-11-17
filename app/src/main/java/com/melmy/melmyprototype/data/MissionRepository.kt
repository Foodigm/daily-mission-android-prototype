package com.melmy.melmyprototype.data

import com.melmy.melmyprototype.utils.runOnIoThread

class MissionRepository private constructor(
        private val db: AppDatabase
) {
    private val missionDao = db.missionDao()
    private val lastAccessDateDao = db.lastAccessDateDao()

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

    fun getMissionsLiveData() =
            missionDao.getAllMissionsLiveData()

    fun getMissions() =
            missionDao.getAllMissions()

    fun getMissionsObservable() =
            missionDao.getAllMissionsObservable()

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

    fun getLastAccessTime() =
            lastAccessDateDao.getLastAccessDate()

    fun accumulatePreviousData() =
            missionDao.accumulatePreviousData()

    fun insertLastAccessDate(accessDate: LastAccessDate) =
            lastAccessDateDao.insertLastAccessDate(accessDate)
//    fun insertDailyMissions(missions: List<DailyMission>) {
//        runOnIoThread {
//            dailyMissionDao.insertMissions(missions)
//        }
//    }
//
//    fun insertDailyMission(mission: DailyMission) {
//        runOnIoThread {
//            dailyMissionDao.insertMission(mission)
//        }
//    }
//
//    fun removeDailyMission(mission: DailyMission) {
//        runOnIoThread {
//            dailyMissionDao.deleteMission(mission)
//        }
//    }
//
//
//    fun updateAccTimeOrCount(dailyMissions: List<DailyMissionJoined>) {
//        runOnIoThread {
//            missionDao.accumulateDailyWorkAndDelete(dailyMissions)
//        }
//    }
//
//    fun getDailyMissions() =
//            dailyMissionDao.getAllDailyMissionsJoined()

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