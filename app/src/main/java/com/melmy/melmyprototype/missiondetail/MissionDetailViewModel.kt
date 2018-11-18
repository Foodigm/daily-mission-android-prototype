package com.melmy.melmyprototype.missiondetail

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionDataSource
import com.melmy.melmyprototype.data.MissionRepository

class MissionDetailViewModel(
        private val repository: MissionRepository
) : ViewModel(), MissionDataSource.GetMissionCallback {
    val mission = ObservableField<Mission>()

    fun start(missionId: Long) {
        getMission(missionId)
    }

    private fun getMission(missionId: Long) {
        repository.getMission(missionId, this)
    }

    override fun onMissionLoaded(mission: Mission) {
        this.mission.set(mission)
    }

    override fun onDataNotAvailable() {
        // error msg
    }
}