package com.melmy.melmyprototype.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MissionDao {
    @Insert
    fun insertMission(mission: Mission)

    @Query("SELECT * FROM missions")
    fun getAllMissions(): LiveData<List<Mission>>

    @Delete
    fun deleteMission(mission: Mission)

}