package com.melmy.melmyprototype.utils

import android.content.Context
import com.melmy.melmyprototype.data.AppDatabase
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.viewmodel.MissionListViewModelFactory

object InjectorUtil {

    private fun getMissionRepository(context: Context) =
            MissionRepository.getInstance(AppDatabase.getInstance(context).missionDao())

    fun provideMissionListViewModelFactory(
            context: Context
    ): MissionListViewModelFactory {
        val repository = getMissionRepository(context)
        return MissionListViewModelFactory(repository)
    }
}