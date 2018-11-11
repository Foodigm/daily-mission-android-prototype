package com.melmy.melmyprototype.missionadd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melmy.melmyprototype.data.MissionRepository

class MissionAddViewModelFactory(
        private val missionRepository: MissionRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MissionAddViewModel(missionRepository) as T
    }
}