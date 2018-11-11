package com.melmy.melmyprototype.missiondetail

import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.MissionRepository

class MissionDetailViewModel(
        val repository: MissionRepository
) : ViewModel() {


    fun getMission(missionId: Long) =
            repository.getMission(missionId)
}