package com.melmy.melmyprototype.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "last_access_date")
data class LastAccessDate(
        @PrimaryKey
        @ColumnInfo(name = "id") val id: Long = 0,
        @ColumnInfo(name = "date") val date: Calendar = Calendar.getInstance()
)