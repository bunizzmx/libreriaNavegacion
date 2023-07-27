package com.example.librarynavigation


import android.content.Context
import android.content.Intent


object ZORNavigationExpose {


    @JvmStatic
    fun initModule(context: Context) {

        val intent = Intent(context, MainActivityFlow::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("data", data)
        }
        context.startActivity(intent)
    }
}
