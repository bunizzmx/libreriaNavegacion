package com.example.librarynavigation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mobilityado.intermedio.server.ConnectivityBroadcastReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewmModel @Inject constructor() : ViewModel()  {

    private val receiver = ConnectivityBroadcastReceiver()

    sealed class UiModel {
        class WifiStatus(val isConnected: Boolean) : UiModel()
    }

    private fun connectivityLiveDataToWifiStatusUiModel(isConnected: Boolean?) = UiModel.WifiStatus(
        isConnected ?: false
    )
    val modelNetwork: LiveData<UiModel> = Transformations.map(
        receiver.model,
        ::connectivityLiveDataToWifiStatusUiModel)
}