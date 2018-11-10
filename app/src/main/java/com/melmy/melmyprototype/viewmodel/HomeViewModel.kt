package com.melmy.melmyprototype.viewmodel

import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.MissionRepository

class HomeViewModel(
        val missionRepository: MissionRepository)
    : ViewModel() {

    val dailyMissions = missionRepository.getDailyMissions()

}