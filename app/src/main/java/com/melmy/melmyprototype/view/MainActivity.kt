package com.melmy.melmyprototype.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.melmy.melmyprototype.R
import com.melmy.melmyprototype.db.AppDatabase
import com.melmy.melmyprototype.model.CompletedHistory
import com.melmy.melmyprototype.model.Mission
import com.melmy.melmyprototype.model.MissionType
import com.melmy.melmyprototype.utils.DayOfWeekSet
import java.util.*
import 	androidx.sqlite.db.transaction
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDatabase.getInstance(this)
        val mission = Mission(
                0,
                "test",
                false,
                DayOfWeekSet(),
                MissionType.COUNT,
                0,
                0,
                0,
                0,
                3,
                1,
                0,
                0)
        val history = CompletedHistory(0, mission.id, Calendar.getInstance())
        val executor = Executors.newSingleThreadExecutor()
        button1.setOnClickListener {
            executor.execute {
                db?.missionDao()?.insertMission(mission)
            }

        }
        button2.setOnClickListener {
            executor.execute {
                db?.completedHistoryDao()?.insertCompletedHistory(history)
            }
        }
    }
}
