package com.example.xx

import android.app.Application
import com.example.librarynavigation.common.LanguageUtils
import dagger.hilt.android.HiltAndroidApp



class AppModuleLogin : Application() {
    override fun onCreate() {
        LanguageUtils.setLocale(applicationContext)
        super.onCreate()
    }
}