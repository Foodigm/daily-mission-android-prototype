package com.melmy.melmyprototype.viewmodel

import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.MissionRepository

class HomeViewModel(
        missionRepository: MissionRepository)
    : ViewModel() {

    val dailyMissions = missionRepository.getDailyMissions()
    init {

    }
}