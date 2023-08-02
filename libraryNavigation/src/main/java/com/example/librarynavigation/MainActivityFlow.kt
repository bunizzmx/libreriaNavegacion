package com.example.librarynavigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.common.common.Communicator
import com.example.common.common.ImageButtonEventDispatcher
import com.example.common.common.NavigationManager
import com.example.librarynavigation.databinding.ActivityMainSdkBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityFlow : AppCompatActivity(), Communicator, NavigationManager {
    private lateinit var binding: ActivityMainSdkBinding
    private lateinit var navController: NavController
    private lateinit var activity: MainActivityFlow
    private val mainActivityViewModel: MainActivityViewmModel by viewModels()


    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSdkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        navController = navHostFragment.navController

        Navigation.findNavController(this, R.id.main_fragment)
            .navigate(R.id.loginFrontUrbanoFragment)


        val signup = intent.getStringExtra("ScreenMain")
        val register = intent.getStringExtra("ScreenRegister")
        if (register.equals("ScreenRegister")) {
            //  val action = MainActivityDirections.actionToFragment()
            //     navController.navigate(action)
        } else if (signup.equals("ScreenMain")) {

        }
        activity = this
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.imageButton.setOnClickListener {
            Log.e("Me presionan", "***")
            ImageButtonEventDispatcher.notifyImageButtonClicked()
        }

    }

    override fun changeFragment(titleLabel: String, visibility: Boolean) {
        binding.constraintToolbar.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.constraintProgress.visibility = View.GONE
        binding.toolbar.title = titleLabel
        binding.imageButton.visibility = View.VISIBLE
    }

    override fun changeFragmentProgress(
        titleLabel: String,
        visibility: Boolean,
        part: Int,
        total: Int
    ) {
        binding.constraintToolbar.visibility = View.VISIBLE
        binding.constraintProgress.visibility = View.VISIBLE
        binding.toolbar.setTitle(titleLabel)
        var progress: Int = (100 / total) * part
        binding.contentLoadingProgressBar.progress = progress
        binding.textProgress.text = "$part/$total"
    }

    override fun navigationManager(value: String) {
        when (value) {
            "LoginPass" -> {
                findNavController(R.id.main_fragment).navigate(
                    R.id.loginFragment
                )
            }

            "LoginEmail" -> {
                findNavController(R.id.main_fragment).navigate(
                    R.id.loginFrontUrbanoFragment
                )
            }

            "LoginHome" -> {
                activity.setResult(100)
                val output = Intent()
                output.putExtra("back", "back")
                activity.setResult(RESULT_OK, output)
                activity.finish()
            }
            else -> {
            }
        }
    }
}