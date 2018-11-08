package com.melmy.melmyprototype.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.model.Mission

class MissionListViewModel(val repository: MissionRepository) : ViewModel() {
    val missions = MutableLiveData<List<Mission>>()
}