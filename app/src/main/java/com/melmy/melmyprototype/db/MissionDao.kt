package com.melmy.melmyprototype.db

import androidx.room.Dao
import androidx.room.Insert
import com.melmy.melmyprototype.model.Mission

@Dao
interface MissionDao {
    @Insert
    fun insertMission(mission: Mission)

}