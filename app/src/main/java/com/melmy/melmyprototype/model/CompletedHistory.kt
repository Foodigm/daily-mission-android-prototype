package com.melmy.melmyprototype.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.util.*

@Entity(
        tableName = "completed_histories",
        foreignKeys = [ForeignKey(entity = Mission::class, parentColumns = ["id"], childColumns = ["mission_id"], onDelete = CASCADE)]
)
data class CompletedHistory(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id") val historyId: Long = 0,
        @ColumnInfo(name = "mission_id") val mission_id: Long,
        @ColumnInfo(name = "date") val date: Calendar
)