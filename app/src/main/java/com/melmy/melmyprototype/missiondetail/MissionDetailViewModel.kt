package com.melmy.melmyprototype.missiondetail

import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.MissionRepository

class MissionDetailViewModel(
        private val repository: MissionRepository
) : ViewModel() {


    fun getMission(missionId: Long) =
            repository.getMission(missionId)
}