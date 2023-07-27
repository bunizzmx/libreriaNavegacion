package com.example.librarynavigation.common


import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*


object LanguageUtils {

    val ES_LOCALE = Locale("es")
    private val EN_LOCALE = Locale("en")

    /*

        fun getCurrentLocale(application: com.example.librarynavigation.AppModuleLogin): Locale = application.currentLocale
        fun getCurrentLocaleIsSpanish(application: com.example.librarynavigation.AppModuleLogin): Boolean = (getCurrentLocale(application) == ES_LOCALE)
        fun localeHasChanged(application: com.example.librarynavigation.AppModuleLogin) = application.currentLocale != application.selectedLocale
    */


    fun getSavedLocale(context: Context): Locale {
        val storedLanguage = SharedPreferencesUtils.getLanguageFromSP(context)
        return if (storedLanguage== SharedPreferencesUtils.DEF_STRING_VALUE) {
            val l = if (context.resources.configuration.locale.language == "es") ES_LOCALE else EN_LOCALE
            SharedPreferencesUtils.saveLanguageInSP(context, l.language)
            l
        }
        else {
            Locale(storedLanguage)
        }
    }





    fun setLocale(context: Context): Context {
        val myLocale = getSavedLocale(context)
        Locale.setDefault(myLocale)
        val config = Configuration()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(myLocale)
        } else {
            config.locale = myLocale
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
}