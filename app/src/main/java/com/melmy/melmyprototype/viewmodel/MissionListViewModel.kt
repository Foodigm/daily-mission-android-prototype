package com.melmy.melmyprototype.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionRepository

class MissionListViewModel(val missionRepository: MissionRepository) : ViewModel() {
    var currentFilter = MissionFilterType.ACTIVE_MISSIONS
    var currentOrder = MissionSortType.SORT_BY_NAME
    val missions = Transformations.map(missionRepository.getMissions()) {
        val list = sortMissions(it, currentOrder)
        filterMissions(list, currentFilter)
    }

    fun rearrangeMissions(newOrder: MissionSortType) {
    }

    fun changeFilter(newFilter: MissionFilterType) =
            missions.value?.let {
                filterMissions(it, newFilter)
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
