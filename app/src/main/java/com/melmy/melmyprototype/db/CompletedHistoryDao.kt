package com.melmy.melmyprototype.db

import androidx.room.Dao
import androidx.room.Insert
import com.melmy.melmyprototype.model.CompletedHistory

@Dao
interface CompletedHistoryDao {
    @Insert
    fun insertCompletedHistory(history: CompletedHistory)
}