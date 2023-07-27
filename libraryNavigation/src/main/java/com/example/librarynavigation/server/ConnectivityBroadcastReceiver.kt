package com.mobilityado.intermedio.server

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class ConnectivityBroadcastReceiver : BroadcastReceiver() {

    private val _model = MutableLiveData<Boolean>()
    val model: LiveData<Boolean>
        get() {
            if (_model.value == null) {
                _model.value = true
            }
            return _model
        }


    override fun onReceive(context: Context?, intent: Intent) {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork = cm?.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true
        _model.value = isConnected
    }
}