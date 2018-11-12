package com.melmy.melmyprototype.data

interface MissionDataSource {
    interface GetMissionCallback {
        fun onMissionLoaded(mission: Mission)
        fun onDataNotAvailable()
    }
}