package com.example.librarynavigation

import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.findNavController


/**
 *This class [AppNavigationController] handles the navigation of the screens, all the functions are called from the MainActivity
 */
class AppNavigationController(private val activity: Activity) {
    private val navController: NavController by lazy {
        activity.findNavController(R.id.main_fragment)
    }

    fun navigateOfLoginPass() {
        navController.navigate(com.example.resources.R.id.loginFragment)
    }


    fun navigateOfLoginEmail() {
        navController.navigate(com.example.resources.R.id.loginFrontUrbanoFragment)
    }

    fun navigateOfPassRecover() {
        navController.navigate(com.example.resources.R.id.loginPasswordFragment)
    }

    fun navigateOfIndicationsRecover() {
        navController.navigate(com.example.resources.R.id.recoverPassFragment)
    }
    fun navigateOfLoginHome() {
        activity.setResult(100)
        val output = Intent()
        output.putExtra("back", "back")
        activity.setResult(RESULT_OK, output)
        activity.finish()
    }

    fun cancelNavigation() {
        val output = Intent()
        output.putExtra("cancel", "cancel")
        activity.setResult(RESULT_CANCELED, output)
        activity.finish()
    }

    fun navigateOfWebView() {
        navController.navigate(com.example.resources.R.id.singUpFragment)
    }

    fun navigateOfSignup() {
        navController.navigate(com.example.resources.R.id.loginFrontUrbanoFragment)
    }

    fun navigateOfConfirmPass() {
        navController.navigate(com.example.resources.R.id.singUpFragment)
    }

    fun navigateOfConfirmToken() {
        navController.navigate(com.example.resources.R.id.registerPasswordFragment)
    }
}

//Refactored class