package com.example.librarynavigation


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher


object NavigationExpose {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    @JvmStatic
    fun registerActivityResultLauncher(activityResultLauncher: ActivityResultLauncher<Intent>) {
        this.activityResultLauncher = activityResultLauncher
    }

    @JvmStatic
    fun initModule(context: Context, app: Int, flow: String) {
        val intent = Intent(context, MainActivityFlow::class.java).apply {
            putExtra("appKey", app)
            putExtra("flowType", flow)
        }
        if (context is Activity) {
            activityResultLauncher.launch(intent)
        }
    }
}
