package com.melmy.melmyprototype.missionlistweek

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MissionListWeekViewModel(val repository: MissionRepository) : ViewModel() {
    val missions = ObservableField<List<Mission>>()
    private val disposable = CompositeDisposable()
    fun start(page: Int) {
        disposable.add(
                repository.getMissionsObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { missionsList ->
                            //전체보기
                            if (page == 0)
                                missions.set(missionsList)
                            else {
                                TODO("요일별 미션 리스트 구현")
                            }
                        }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }
}