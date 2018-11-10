package com.melmy.melmyprototype.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DailyMissionDao {
    @Insert
    fun insertMission(mission: DailyMission)

//    @Query("SELECT * FROM daily_missions")
//    fun getAllMissions(): LiveData<List<DailyMission>>

    @Delete
    fun deleteMission(mission: DailyMission)

    @Query("SELECT daily_missions.*, missions.title, missions.type, missions.goal_minute_daily, missions.goal_count_daily " +
            "FROM daily_missions " +
            "INNER JOIN missions " +
            "ON daily_missions.mission_id = missions.id")
    fun getAllDailyMissionsJoined(): LiveData<List<DailyMissionJoined>>
}