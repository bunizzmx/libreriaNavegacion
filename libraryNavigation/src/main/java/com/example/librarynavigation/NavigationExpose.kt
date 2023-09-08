package com.example.librarynavigation


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.result.ActivityResultLauncher


object NavigationExpose {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    fun registerActivityResultLauncher(activityResultLauncher: ActivityResultLauncher<Intent>) {
        this.activityResultLauncher = activityResultLauncher
    }

    fun initModule(context: Context, app: Int) {
        val intent = Intent(context, MainActivityFlow::class.java).apply {
            putExtra("appKey", app)
        }
        if (context is Activity) {
            activityResultLauncher.launch(intent)
        }
    }
}
