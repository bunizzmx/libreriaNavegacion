package com.example.librarynavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.common.common.Communicator
import com.example.common.common.ImageButtonEventDispatcher
import com.example.common.common.NavigationManager
import com.example.featureloginhome.ui.uii.LoginFrontUrbanoFragment
import com.example.featureloginscreen.ui.LoginFragment
import com.example.featurepasslogin.ui.ui.LoginPasswordFragment
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


        if (appKey == 1) {
            preferenceManager.saveInt(PreferenceManager.ID_COMPANY, appKey)
            when (flowType) {
                "Login" -> {
                    navigationController.navigateOfLoginPass()
                }

                "Register" -> {
                    navigationController.navigateOfWebView()
                }
            }
        } else {
            preferenceManager.saveInt(PreferenceManager.ID_COMPANY, appKey)
            navigationController.navigateOfLoginEmail()
        }

        val idCompany = preferenceManager.getInt(PreferenceManager.ID_COMPANY, 0)
        binding.appId = idCompany


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
            binding.constraintProgress.visibility = View.VISIBLE
        } else if (flowTypeShowToolbar == "Login") {
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
        val idCompany = preferenceManager.getInt(PreferenceManager.ID_COMPANY, 0)
        when (value) {
            "LoginPass" -> {
                try {
                    Log.e("OPTION-TEST", "OP1")
                    navigationController.navigateOfLoginPass()
                } catch (e: Exception) {
                    Log.e("OPTION-TEST", e.toString())
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, LoginFragment())
                        .commit()
                }
            }

            "GoScreenPass" -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, LoginPasswordFragment())
                    .commit()
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