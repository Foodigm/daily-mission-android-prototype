package com.melmy.melmyprototype.missionlist

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionRepository

class MissionListViewModel(val missionRepository: MissionRepository) : ViewModel() {
    var currentFilter = MissionFilterType.ACTIVE_MISSIONS
    var currentOrder = MissionSortType.SORT_BY_NAME
    val dbMissions = missionRepository.getMissions()
    val missions = MediatorLiveData<List<Mission>>()

    init {
        missions.addSource(dbMissions) { it ->
            it?.let {
                Log.d("sgc109", "addSource!")
                val list = sortMissions(it, currentOrder)
                missions.value = filterMissions(list, currentFilter)
            }
        }
    }

    fun changeOrder(newOrder: MissionSortType) =
            dbMissions.value?.let {
                val list = sortMissions(it, newOrder)
                missions.value = filterMissions(list, currentFilter)
            }.also {
                currentOrder = newOrder
            }

    fun changeFilter(newFilter: MissionFilterType) =
            dbMissions.value?.let {
                val list = sortMissions(it, currentOrder)
                missions.value = filterMissions(list, newFilter)
            }.also {
                currentFilter = newFilter
            }

    fun sortMissions(list: List<Mission>, sortType: MissionSortType): List<Mission> =
            when (sortType) {
                MissionSortType.SORT_BY_NAME -> list.sortedWith(compareBy({ it.title }))
                else -> list
            }

    fun filterMissions(list: List<Mission>, filter: MissionFilterType) =
            list.filter {
                when (filter) {
                    MissionFilterType.ACTIVE_MISSIONS -> !it.isCompleted
                    MissionFilterType.COMPLETED_MISSIONS -> it.isCompleted
                    else -> true
                }
            }
}