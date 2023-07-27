package com.example.librarynavigation

import android.app.Application
import com.example.librarynavigation.common.LanguageUtils
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AppModuleLogin : Application() {
    override fun onCreate() {
        LanguageUtils.setLocale(applicationContext)
        super.onCreate()
    }
}