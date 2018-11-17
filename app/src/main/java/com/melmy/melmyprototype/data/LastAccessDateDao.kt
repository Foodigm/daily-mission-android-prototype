package com.melmy.melmyprototype.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import java.util.*

@Dao
interface LastAccessDateDao {
    @Query("SELECT * from last_access_date")
    fun getLastAccessDate(): Maybe<LastAccessDate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastAccessDate(accessDate: LastAccessDate)
}