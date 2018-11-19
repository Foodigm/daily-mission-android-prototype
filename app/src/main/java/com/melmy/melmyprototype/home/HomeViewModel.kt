package com.melmy.melmyprototype.home

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.LastAccessDate
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.utils.isToday
import com.melmy.melmyprototype.utils.runOnIoThread
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class HomeViewModel(
        private val missionRepository: MissionRepository)
    : ViewModel() {

    val dailyMissions = ObservableField<List<Mission>>()
    private val disposable = CompositeDisposable()
    val dailyMissionsLiveData = MutableLiveData<List<Mission>>()

    fun updateDailyMission(mission: Mission) =
            runOnIoThread { missionRepository.updateMission(mission) }

    // stream 이 도중에 끊기는 경우를 생각해야함
    fun start() {
        Log.d("sgc109", "start!")
        disposable.add(
                missionRepository.getLastAccessTime()
                        .toObservable()
                        .subscribeOn(Schedulers.io())
                        .onErrorResumeNext(Observable.just(LastAccessDate()))
                        .flatMap { lastDate ->
                            Log.d("sgc109", "#1")
                            if (!isToday(lastDate.date)) {
                                Log.d("sgc109", "acc!")
                                missionRepository.accumulatePreviousData()
                            }
                            Log.d("sgc109", "#2")

                            missionRepository.insertLastAccessDate(LastAccessDate())
                            Log.d("sgc109", "#3")

                            missionRepository.getMissionsObservable()

                        }
                        .take(1)
                        .map { missions ->
                            Log.d("sgc109", "#4")

                            val daily = ArrayList<Mission>()
                            for (mission in missions) {
                                if (mission.days.isTodayOn() && mission.isCompleted == false) {
                                    daily.add(mission)
                                }
                            }
                            daily
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("sgc109", "#5")
                            dailyMissions.set(it)
                            dailyMissionsLiveData.value = it
                            Log.d("sgc109", "#6")
                        }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
        Log.d("sgc109", "dispoased and cleared!")
    }
}

data class MyPair(
        val doesDailyMissionExist: Boolean,
        val list: Any
)