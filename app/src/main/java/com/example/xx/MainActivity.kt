package com.example.xx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.librarynavigation.ZORNavigationExpose
import dagger.hilt.android.AndroidEntryPoint



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ZORNavigationExpose.initModule(this)
    }
}