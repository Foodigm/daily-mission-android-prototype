package com.melmy.melmyprototype.missionaddedit

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.melmy.melmyprototype.data.Mission
import com.melmy.melmyprototype.data.MissionDataSource
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.utils.DayOfWeekSet

class MissionAddEditViewModel(
        val repository: MissionRepository
) : ViewModel(), MissionDataSource.GetMissionCallback {
    val mission = ObservableField<Mission>()
    val missionLoaded = ObservableField<Boolean>()

    fun start(missionId: Long) {
        mission.set(Mission())
        missionLoaded.set(false)
        if (missionId != -1L) {
            getMission(missionId)
        }
    }

    private fun getMission(missionId: Long) {
        repository.getMission(missionId, this)
    }

    override fun onMissionLoaded(mission: Mission) {
        this.mission.set(mission)
        this.missionLoaded.set(true)
    }

    override fun onDataNotAvailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun updateMission(newTitle: String, newDays: DayOfWeekSet) {
        val mission = mission.get()
        mission?.let {
            it.title = newTitle
            it.days = newDays
            repository.updateMission(it)
        }
    }

}