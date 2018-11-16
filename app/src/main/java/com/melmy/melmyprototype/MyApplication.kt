package com.melmy.melmyprototype

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        //벡터 이미지 활성화
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}