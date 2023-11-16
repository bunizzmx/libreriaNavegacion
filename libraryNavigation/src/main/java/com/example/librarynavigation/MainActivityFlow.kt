package com.example.librarynavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.common.common.Communicator
import com.example.common.common.ImageButtonEventDispatcher
import com.example.common.common.NavigationManager
import com.example.featureloginhome.ui.uii.LoginFrontUrbanoFragment
import com.example.featureloginscreen.ui.LoginFragment
import com.example.featuresigup.ui.SingUpFragment
import com.example.librarynavigation.databinding.ActivityMainSdkBinding
import com.example.resourcesdata.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityFlow : AppCompatActivity(), Communicator, NavigationManager {
    private lateinit var binding: ActivityMainSdkBinding
    private lateinit var activity: MainActivityFlow
    private var flowTypeShowToolbar = ""

    private val navigationController by lazy {
        AppNavigationController(this)
    }
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferenceManager = PreferenceManager(this)

        binding = ActivityMainSdkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appKey = intent.getIntExtra("appKey", 0)


        val flowType = intent.getStringExtra("flowType")
        flowTypeShowToolbar = flowType!!


        if (appKey== 1) {
            if (savedInstanceState == null) {
                preferenceManager.saveInt(PreferenceManager.ID_COMPANY, appKey)
               when (flowType) {
                   "Login" -> {
                       supportFragmentManager.beginTransaction()
                           .replace(R.id.main_fragment, LoginFragment())
                           .commit()
                   }
                   "Register" -> {
                       supportFragmentManager.beginTransaction()
                           .replace(R.id.main_fragment, SingUpFragment())
                           .commit()
                   }
               }
            }
        }  else {
            if (savedInstanceState == null) {
                preferenceManager.saveInt(PreferenceManager.ID_COMPANY, appKey)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, LoginFrontUrbanoFragment())
                    .commit()
            }
        }

        val idCompany = preferenceManager.getInt(PreferenceManager.ID_COMPANY,0)
        binding.appId = idCompany


        navigationController.navigateOfLoginEmail()


        activity = this
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.imageButton.setOnClickListener {
            ImageButtonEventDispatcher.notifyImageButtonClicked()
        }

    }

    override fun changeFragment(titleLabel: String, visibility: Boolean) {
        var titleNew = titleLabel
        var visibilityNew = visibility
        if (flowTypeShowToolbar == "Register") {
            titleNew = "Regístrate"
            visibilityNew = true
            binding.constraintProgress.visibility = View.VISIBLE
            binding.toolbar.setTitle(titleLabel)
            var progress: Int = (100 / 3) * 1
            binding.contentLoadingProgressBar.progress = progress
            binding.textProgress.text = "1/3"
        } else if (flowTypeShowToolbar == "Login") {
            titleNew = "Iniciar sesión"
            visibilityNew = false
            binding.constraintProgress.visibility = View.GONE
        } else {
            binding.constraintProgress.visibility = View.GONE
        }
        binding.constraintToolbar.visibility = if (visibilityNew) View.VISIBLE else View.GONE
        binding.toolbar.title = titleNew
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
        val preferenceManager = PreferenceManager(this)
        val idCompany = preferenceManager.getInt(PreferenceManager.ID_COMPANY,0)
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
                if (idCompany == 1) navigationController.cancelNavigation()
                else {
                    navigationController.navigateOfLoginEmail()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, LoginFrontUrbanoFragment())
                        .commit()
                }
            }

            "LoginHome" -> navigationController.cancelNavigation()
            "WebView" -> navigationController.navigateOfWebView()
            "Signup" -> {
                if (idCompany == 1) navigationController.cancelNavigation()
                else navigationController.navigateOfSignup()
            }
            "CreatePass" -> {
                try {
                    navigationController.navigateOfConfirmPass()
                } catch (e: Exception) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, SingUpFragment())
                        .commit()
                }
            }
            "ConfirmToken" -> navigationController.navigateOfConfirmToken()
            "LoginRecoverPass" -> navigationController.navigateOfPassRecover()
            "PassRecoverInstructions" -> navigationController.navigateOfIndicationsRecover()
        }
    }
}