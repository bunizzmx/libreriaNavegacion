package com.example.xx

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.librarynavigation.NavigationExpose
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Crear el launcher para recibir el resultado de MainActivityFlow
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                // Aquí manejas los datos devueltos desde la pantalla pass
                val userData = data?.getStringExtra("UserData")
                Log.e("RESULTADO", userData.toString())
            }
        }

        // Registrar el launcher en el objeto NavigationExpose
        NavigationExpose.registerActivityResultLauncher(activityResultLauncher)
        NavigationExpose.initModule(this, 5)
    }
}