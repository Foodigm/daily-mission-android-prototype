package com.melmy.melmyprototype.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.melmy.melmyprototype.model.DailyMission

@Dao
interface DailyMissionDao {
    @Insert
    fun insertMission(mission: DailyMission)

    @Query("SELECT * FROM daily_missions")
    fun getAllMissions(): LiveData<List<DailyMission>>

    @Delete
    fun deleteMission(mission: DailyMission)
}