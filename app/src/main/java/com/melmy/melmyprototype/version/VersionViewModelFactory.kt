package com.melmy.melmyprototype.version

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VersionViewModelFactory(
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VersionViewModel() as T
    }
}