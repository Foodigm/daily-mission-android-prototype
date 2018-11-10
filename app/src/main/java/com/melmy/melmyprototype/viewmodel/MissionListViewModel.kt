package com.melmy.melmyprototype.viewmodel

import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.MissionRepository

class MissionListViewModel(val missionRepository: MissionRepository) : ViewModel() {
    val missions = missionRepository.getMissions()
}