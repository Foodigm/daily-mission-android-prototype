package com.melmy.melmyprototype.data

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Observable

@Dao
interface MissionDao {
    @Insert
    fun insertMission(mission: Mission)

    @Query("SELECT * FROM missions")
    fun getAllMissionsLiveData(): LiveData<List<Mission>>

    @Query("SELECT * FROM missions")
    fun getAllMissions(): List<Mission>

    @Query("SELECT * FROM missions")
    fun getAllMissionsObservable(): Observable<List<Mission>>

    @Query("SELECT * FROM missions WHERE id = :missionId")
    fun getMission(missionId: Long): Mission?

    @Delete
    fun deleteMission(mission: Mission)

    @Query("UPDATE missions " +
            "SET acc_minute_total = acc_minute_total + acc_minute_daily, " +
            "acc_count_total = acc_count_total + acc_count_daily, " +
            "acc_minute_daily = 0, " +
            "acc_count_daily = 0 " +
            "WHERE acc_minute_daily > 0 AND acc_count_daily > 0 ")
    fun accumulatePreviousData(): Int

    @Update
    fun updateMission(mission: Mission): Int
}