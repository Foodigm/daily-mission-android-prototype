package com.melmy.melmyprototype.home

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.DailyMission
import com.melmy.melmyprototype.data.DailyMissionJoined
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.utils.isSameDay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(
        private val missionRepository: MissionRepository)
    : ViewModel() {

    val dailyMissions = ObservableField<List<DailyMissionJoined>>()

    fun start() {
        missionRepository
                .getDailyMissions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    var check = false
                    if (!it.isEmpty()) {
                        val date = it.get(0).dailyMission.date
                        val today = Calendar.getInstance()
                        if (!isSameDay(date, today)) {
                            check = true
                            missionRepository.updateAccTimeOrCount(it)
                        }
                    } else check = true
                    if (check) {
                        missionRepository.getMissionsObservable()
                    } else {
                        dailyMissions.set(it)
                        throw Exception()
                    }
                }
                .doOnTerminate {
                    Log.d("sgc109", "doOnTerminate")
                }
                .map {
                    val missions = ArrayList<Mission>()
                    for (mission in it) {
                        if (mission.days.isTodayOn() == true && mission.isCompleted != true) {
                            missions.add(mission)
                        }
                    }
                    missions as List<Mission>
                }
                .subscribe {
                    val dailyMissions = ArrayList<DailyMission>()
                    // dailyMission 의 List 만들어서 DB 에 insert 하고 observable 에 set
                }
    }
}