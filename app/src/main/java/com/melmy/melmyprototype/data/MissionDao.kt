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
            "SET acc_seconds_total = acc_seconds_total + acc_seconds_daily, " +
            "acc_counts_total = acc_counts_total + acc_counts_daily, " +
            "acc_seconds_daily = 0, " +
            "acc_counts_daily = 0 " +
            "WHERE acc_seconds_daily > 0 AND acc_counts_daily > 0 ")
    fun accumulatePreviousData(): Int

    @Update
    fun updateMission(mission: Mission): Int

    @Query("DELETE FROM missions")
    fun deleteAllMissions()
}