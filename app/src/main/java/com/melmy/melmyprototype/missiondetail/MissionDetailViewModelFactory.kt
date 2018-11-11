package com.melmy.melmyprototype.missiondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melmy.melmyprototype.data.MissionRepository

class MissionDetailViewModelFactory(
        val repository: MissionRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MissionDetailViewModel(repository) as T
    }
}