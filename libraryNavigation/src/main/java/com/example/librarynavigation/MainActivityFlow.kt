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
import com.example.featureloginhome.ui.ui.LoginFrontUrbanoFragment
import com.example.featureloginscreen.ui.LoginFragment
import com.example.librarynavigation.databinding.ActivityMainSdkBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityFlow : AppCompatActivity(), Communicator, NavigationManager {
    private lateinit var binding: ActivityMainSdkBinding
    private lateinit var navController: NavController
    private lateinit var activity: MainActivityFlow
    private val mainActivityViewModel: MainActivityViewmModel by viewModels()

    private val navigationController by lazy {
        AppNavigationController(this)
    }
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


        val register = intent.getStringExtra("ScreenRegister")
        if (register.equals("ScreenRegister")) navigationController.navigateOfConfirmPass()


        activity = this
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.imageButton.setOnClickListener {
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
                try {
                    navigationController.navigateOfLoginPass()
                } catch (e: Exception) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, LoginFragment())
                        .commit()
                }
            }

            "LoginEmail" -> {
                navigationController.navigateOfLoginEmail()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, LoginFrontUrbanoFragment())
                    .commit()
            }

            "LoginHome" -> navigationController.cancelNavigation()
            "WebView" -> navigationController.navigateOfWebView()
            "Signup" -> navigationController.navigateOfSignup()
            "CreatePass" -> navigationController.navigateOfConfirmPass()
            "ConfirmToken" -> navigationController.navigateOfConfirmToken()
            "LoginRecoverPass" -> navigationController.navigateOfPassRecover()
            "PassRecoverInstructions" -> navigationController.navigateOfIndicationsRecover()
            else -> {
            }
        }
    }
}