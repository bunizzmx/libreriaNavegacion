package com.example.librarynavigation.common

import android.content.Context
import android.content.SharedPreferences


object SharedPreferencesUtils {
    const val DEF_STRING_VALUE = "DEF_STRING_VALUE"
    private const val DEF_BOOLEAN_VALUE = false
    private const val DEF_LONG_VALUE = 0.toLong()
    private const val LOCALE_KEY = "LOCALE_KEY"
    private const val CODE_KEY = "CODE_KEY"
    private const val PUSH_NOTIFICATIONS_ARE_ENABLED = "PUSH_NOTIFICATIONS_ARE_ENABLED"
    private const val INCIDENT_LAST_SYNC = "INCIDENT_LAST_SYNC"
    private const val CARDS_BALANCES_LAST_SYNC = "CARDS_BALANCES_LAST_SYNC"
    private const val RECHARGING_POINTS_LAST_SYNC = "RECHARGING_POINTS_LAST_SYNC"
    private const val POIS_LAST_SYNC = "POIS_LAST_SYNC"
    private const val POIS_VERSION = "POIS_VERSION"
    private const val RECHARGING_VERSION = "RECHARGING_VERSION"


    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("QROAPP_SP", Context.MODE_PRIVATE)


    private fun writeStringToSharedPreferences(context: Context, key: String, value: String) {
        with (getSharedPreferences(context).edit()) {
            putString(key, value)
            commit()
        }
    }

    private fun writeBooleanToSharedPreferences(context: Context, key: String, value: Boolean) {
        with (getSharedPreferences(context).edit()) {
            putBoolean(key, value)
            commit()
        }
    }

    private fun writeLongToSharedPreferences(context: Context, key: String, value: Long) {
        with (getSharedPreferences(context).edit()) {
            putLong(key, value)
            commit()
        }
    }


    private fun readStringFromSharedPreferences(context: Context, key: String): String =
        getSharedPreferences(context).getString(key, DEF_STRING_VALUE)!!

    private fun readBooleanFromSharedPreferences(context: Context, key: String): Boolean =
        getSharedPreferences(context).getBoolean(key, DEF_BOOLEAN_VALUE)

    private fun readLongFromSharedPreferences(context: Context, key: String): Long =
        getSharedPreferences(context).getLong(key, DEF_LONG_VALUE)

    fun saveUserSessionCode(context: Context, code: String) =
        writeStringToSharedPreferences(context, CODE_KEY, code)

    fun saveLanguageInSP(context: Context, language: String) =
        writeStringToSharedPreferences(context, LOCALE_KEY, language)

    fun saveIncidentLastSync(context: Context, lastSync: Long) =
        writeLongToSharedPreferences(context, INCIDENT_LAST_SYNC, lastSync)

    fun saveCardsBalancesLastSync(context: Context, lastSync: Long) =
        writeLongToSharedPreferences(context, CARDS_BALANCES_LAST_SYNC, lastSync)

    fun saveRechargingPointsLastSync(context: Context, lastSync: Long) =
        writeLongToSharedPreferences(context, RECHARGING_POINTS_LAST_SYNC, lastSync)

    fun savePOIsLastSync(context: Context, lastSync: Long) =
        writeLongToSharedPreferences(context, POIS_LAST_SYNC, lastSync)

    fun getUserSessionCode(context: Context) =
        readStringFromSharedPreferences(context, CODE_KEY)

    fun getLanguageFromSP(context: Context) =
        readStringFromSharedPreferences(context, LOCALE_KEY)

    fun getPushNotificationsAreEnabled(context: Context): Boolean =
        readBooleanFromSharedPreferences(context, PUSH_NOTIFICATIONS_ARE_ENABLED)

    fun setPushNotificationsAreEnabled(context: Context, enabled: Boolean) =
        writeBooleanToSharedPreferences(context, PUSH_NOTIFICATIONS_ARE_ENABLED, enabled)

    fun getIncidentLastSync(context: Context) =
        readLongFromSharedPreferences(context, INCIDENT_LAST_SYNC)

    fun getCardsBalancesLastSync(context: Context) =
        readLongFromSharedPreferences(context, CARDS_BALANCES_LAST_SYNC)

    fun getRechargingPointsLastSync(context: Context) =
        readLongFromSharedPreferences(context, RECHARGING_POINTS_LAST_SYNC)

    fun getPOIsLastSync(context: Context) =
        readLongFromSharedPreferences(context, POIS_LAST_SYNC)

    fun savePOISVersion(context: Context, version: Long) = writeLongToSharedPreferences(context, POIS_VERSION, version)

    fun getPOISVersion(context: Context) = readLongFromSharedPreferences(context, POIS_VERSION)

    fun saveRechargingPointVersion(context: Context, version: Long) = writeLongToSharedPreferences(context, RECHARGING_VERSION, version)

    fun getRechargingPointVersion(context: Context) = readLongFromSharedPreferences(context, RECHARGING_VERSION)
}