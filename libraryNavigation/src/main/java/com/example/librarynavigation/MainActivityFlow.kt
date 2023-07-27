package com.example.librarynavigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.featureloginhome.ui.ui.LoginFrontUrbanoFragment
import com.example.librarynavigation.databinding.ActivityMainSdkBinding
import com.example.librarynavigation.utils.Communicator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivityFlow : AppCompatActivity(),Communicator {
    private lateinit var binding: ActivityMainSdkBinding
    private lateinit var navController: NavController
    private lateinit var activity: MainActivityFlow
    private val mainActivityViewModel : MainActivityViewmModel by viewModels()


    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSdkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, LoginFrontUrbanoFragment()) //
                .commit()
        }

      // Navigation.findNavController(this,R.id.main_fragment).navigate(R.id.loginFrontUrbanoFragment)


        val signup = intent.getStringExtra("ScreenMain")
        val register = intent.getStringExtra("ScreenRegister")
        if (register.equals("ScreenRegister")) {
          //  val action = MainActivityDirections.actionToFragment()
       //     navController.navigate(action)
        }
        else if (signup.equals("ScreenMain")){

        }
        activity = this
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.imageButton.setOnClickListener { backfragment() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController(R.id.main_fragment).currentDestination?.id

        when(findNavController(R.id.main_fragment).currentDestination?.id) {
          /*  R.id.loginFragment ->
            {
                findNavController(R.id.main_fragment).navigate(
                    R.id.loginFragment
                )
            }
            R.id.loginFragment ->
            {
                findNavController(R.id.main_fragment).navigate(
                    R.id.loginFrontUrbanoFragment
                )
            }
            R.id.loginFrontUrbanoFragment ->
            {
                activity.setResult(100)
                val output = Intent()
                output.putExtra("back","back")
                activity.setResult(RESULT_OK,output)
                activity.finish()
            }else -> {
            super.onBackPressed()
        } */
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun changeFragment(titleLabel: String, visibility: Boolean) {
        binding.constraintToolbar.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.constraintProgress.visibility = View.GONE
        binding.toolbar.title = titleLabel
        binding.imageButton.visibility = View.VISIBLE
    }

    override fun backfragment() {
        activity.onBackPressed()
    }

    override fun changeFragmentProgress(
        titleLabel: String,
        visibility: Boolean,
        part: Int,
        total: Int
    ) {
        binding.constraintToolbar.visibility = View.VISIBLE
        binding.constraintProgress.visibility =  View.VISIBLE
        binding.toolbar.setTitle(titleLabel)
        var progress : Int = (100 / total) * part
        binding.contentLoadingProgressBar.progress = progress
        binding.textProgress.text = "$part/$total"
    }


}