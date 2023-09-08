package com.example.librarynavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.common.common.Communicator
import com.example.common.common.ImageButtonEventDispatcher
import com.example.common.common.NavigationManager
import com.example.featureloginhome.ui.uii.LoginFrontUrbanoFragment
import com.example.featureloginscreen.ui.LoginFragment
import com.example.librarynavigation.databinding.ActivityMainSdkBinding
import com.example.resourcesdata.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityFlow : AppCompatActivity(), Communicator, NavigationManager {
    private lateinit var binding: ActivityMainSdkBinding
    private lateinit var activity: MainActivityFlow

    private val navigationController by lazy {
        AppNavigationController(this)
    }
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferenceManager = PreferenceManager(this)

        binding = ActivityMainSdkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val appKey = intent.getIntExtra("appKey", 0)
            preferenceManager.saveInt(PreferenceManager.ID_COMPANY, appKey) // Para pruebas controlaremos el idCompany de manera manual
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, LoginFrontUrbanoFragment())
                .commit()
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