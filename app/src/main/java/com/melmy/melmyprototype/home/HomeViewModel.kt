package com.melmy.melmyprototype.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.DailyMission
import com.melmy.melmyprototype.data.DailyMissionJoined
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.utils.isSameDay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel(
        private val missionRepository: MissionRepository)
    : ViewModel() {

    private val dailyMissions = ObservableField<List<DailyMissionJoined>>()
    private val disposable = CompositeDisposable()

    // stream 이 도중에 끊기는 경우를 생각해야함
    fun start() {
        disposable.add(missionRepository
                .getDailyMissions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .flatMap {
                    var check = false
                    if (!it.isEmpty()) {
                        val date = it.get(0).dailyMission.createdDate
                        val today = Calendar.getInstance()
                        if (!isSameDay(date, today)) {
                            check = true
                            missionRepository.updateAccTimeOrCount(it)
                        }
                    } else check = true
                    if (check) {
                        missionRepository.getMissionsObservable()
                    } else {
                        Observable.just(it)
                    }
                }
                .take(1)
                .map {
                    if (it.size > 0 && it.get(0) is Mission) {
                        val missions = ArrayList<Mission>()
                        for (mission in it as List<Mission>) {
                            if (mission.days.isTodayOn() == true && mission.isCompleted != true) {
                                missions.add(mission)
                            }
                        }

                        val dailyMissions = ArrayList<DailyMission>()
                        val dailyMissionsJoined = ArrayList<DailyMissionJoined>()
                        // dailyMission 의 List 만들어서 DB 에 insert 하고 observable 에 set
                        for (item in missions) {
                            val dailyMission = DailyMission(missionId = item.id)
                            val dailyMissionJoined = DailyMissionJoined(dailyMission = dailyMission, title = item.title, type = item.type)
                            dailyMissions.add(dailyMission)
                            dailyMissionsJoined.add(dailyMissionJoined)
                        }
                        missionRepository.insertDailyMissions(dailyMissions)

                        dailyMissionsJoined

                    } else {
                        it as List<DailyMissionJoined>
                    }
                }
                .subscribe {
                    dailyMissions.set(it)
                    Log.d("sgc109", "daily item count : " + it.size)
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }
}

data class MyPair(
        val doesDailyMissionExist: Boolean,
        val list: Any
)