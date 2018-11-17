package com.melmy.melmyprototype.home

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.utils.runOnIoThread
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(
        private val missionRepository: MissionRepository)
    : ViewModel() {

    private val dailyMissions = ObservableField<List<Mission>>()
    private val disposable = CompositeDisposable()

    // stream 이 도중에 끊기는 경우를 생각해야함
//    fun start() {
//        disposable.add(missionRepository
//                .getDailyMissions()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .take(1)
//                .flatMap {
//                    if (!it.isEmpty()) {
//                        val date = it.get(0).dailyMission.createdDate
//                        val today = Calendar.getInstance()
//                        if (!isSameDay(date, today)) {
//                            missionRepository.updateAccTimeOrCount(it)
//                        }
//                    }
//                    missionRepository.getMissionsObservable()
//                }
//                .take(1)
//                .map {
//                    val missions = ArrayList<Mission>()
//                    for (mission in it) {
//                        if (mission.days.isTodayOn() == true && mission.isCompleted != true) {
//                            missions.add(mission)
//                        }
//                    }
//
//                    val dailyMissions = ArrayList<DailyMission>()
//                    val dailyMissionsJoined = ArrayList<DailyMissionJoined>()
//                    // dailyMission 의 List 만들어서 DB 에 insert 하고 observable 에 set
//                    for (item in missions) {
//                        val dailyMission = DailyMission(missionId = item.id)
//                        val dailyMissionJoined = DailyMissionJoined(dailyMission = dailyMission, title = item.title, type = item.type)
//                        dailyMissions.add(dailyMission)
//                        dailyMissionsJoined.add(dailyMissionJoined)
//                    }
//                    missionRepository.insertDailyMissions(dailyMissions)
//
//                    dailyMissionsJoined
//                }
//                .subscribe {
//                    dailyMissions.set(it)
//                    Log.d("sgc109", "daily item count : " + it.size)
//                })
//    }
//
//    fun start() {
//        disposable.add(
//                missionRepository.getLastAccessTime() // 저장된 LastAcessDate 가 없는 경우도 생각해야됨. 최초 실행시
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .switchIfEmpty(Maybe.just(LastAccessDate(date = Calendar.getInstance().apply { add(Calendar.DATE, -1) })))
//                        .flatMapObservable {
//                            if (isToday(it.date) != true) {
//                                missionRepository.insertLastAccessDate(LastAccessDate())
//                                Observable.just(true)
//                            } else {
//                                Observable.just(false)
//                            }
//                        }
//                        .take(1)
//                        .flatMap {
//                            if (it) {
//                                Observable.just(missionRepository.accumulatePreviousData())
//                            } else {
//                                Observable.just(0)
//                            }
//                        }
//                        .flatMap {
//                            missionRepository.getMissionsObservable()
//                        }
//                        .take(1)
//                        .subscribe {
//                            val todaysMissions = ArrayList<Mission>()
//                            for (mission in it) {
//                                if (mission.days.isTodayOn()) {
//                                    todaysMissions.add(mission)
//                                }
//                            }
//                            dailyMissions.set(todaysMissions)
//                        }
//        )
//    }

    fun start(){
        runOnIoThread {
        }
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