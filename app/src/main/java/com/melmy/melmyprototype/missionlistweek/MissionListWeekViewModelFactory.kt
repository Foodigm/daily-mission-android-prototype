package com.melmy.melmyprototype.missionlistweek

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melmy.melmyprototype.data.MissionRepository

class MissionListWeekViewModelFactory(
        private val repository: MissionRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MissionListWeekViewModel(repository) as T
    }

}
