package com.melmy.melmyprototype.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.melmy.melmyprototype.data.AppDatabase
import com.melmy.melmyprototype.data.MissionRepository
import com.melmy.melmyprototype.home.HomeViewModelFactory
import com.melmy.melmyprototype.missionaddedit.MissionAddEditViewModelFactory
import com.melmy.melmyprototype.missiondetail.MissionDetailViewModelFactory
import com.melmy.melmyprototype.missionlist.MissionListViewModelFactory
import com.melmy.melmyprototype.missionlistweek.MissionListWeekViewModelFactory

object InjectorUtil {

    private fun getMissionRepository(context: Context) =
            MissionRepository.getInstance(
                    AppDatabase.getInstance(context)
            )

    fun provideMissionListViewModelFactory(
            context: Context
    ): MissionListViewModelFactory {
        val missionRepository = getMissionRepository(context)
        return MissionListViewModelFactory(missionRepository)
    }

    fun provideHomeViewModelFactory(
            context: Context
    ): HomeViewModelFactory {
        val missionRepository = getMissionRepository(context)
        return HomeViewModelFactory(missionRepository)
    }

    fun provideMissionAddViewModelFactory(
            context: Context
    ): MissionAddEditViewModelFactory {
        return MissionAddEditViewModelFactory(getMissionRepository(context))
    }

    fun provideMissionDetailViewModelFactory(
            context: Context
    ): MissionDetailViewModelFactory {
        return MissionDetailViewModelFactory(getMissionRepository(context))
    }

    fun provideMissionListWeekViewModelFactory(
            context: Context
    ): ViewModelProvider.Factory? {
        return MissionListWeekViewModelFactory(getMissionRepository(context))
    }
}