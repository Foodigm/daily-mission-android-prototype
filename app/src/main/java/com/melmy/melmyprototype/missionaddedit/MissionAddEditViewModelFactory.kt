package com.melmy.melmyprototype.missionaddedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melmy.melmyprototype.data.MissionRepository

class MissionAddEditViewModelFactory(
        private val missionRepository: MissionRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MissionAddEditViewModel(missionRepository) as T
    }
}