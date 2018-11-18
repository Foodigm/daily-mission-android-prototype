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
    fun start() {
        disposable.add(
                repository.getMissionsObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { missionsList ->
                            missions.set(missionsList)
                        }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
        disposable.clear()
    }
}