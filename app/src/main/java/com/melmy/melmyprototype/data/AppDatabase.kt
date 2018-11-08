package com.melmy.melmyprototype.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.melmy.melmyprototype.model.CompletedHistory
import com.melmy.melmyprototype.model.Mission
import com.melmy.melmyprototype.utils.DATABASE_NAME

@Database(entities = [Mission::class, CompletedHistory::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun missionDao(): MissionDao
    abstract fun completedHistoryDao(): CompletedHistoryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                        .build()
                        .also {
                            instance = it
                        }
            }
        }
    }
}