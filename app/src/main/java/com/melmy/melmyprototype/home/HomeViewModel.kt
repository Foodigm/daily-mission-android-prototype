package com.melmy.melmyprototype.home

import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.MissionRepository

class HomeViewModel(
        private val missionRepository: MissionRepository)
    : ViewModel() {

    val dailyMissions = missionRepository.getDailyMissions()

}