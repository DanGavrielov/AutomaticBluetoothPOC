package com.example.automaticbluetoothpoc.viewModel

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

@SuppressLint("MissingPermission")
class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(UIState())
        private set

    fun discoveryStarted() {
        uiState = uiState.copy(
            deviceList = emptyList(),
            discoveryOngoing = true
        )
    }

    fun discoveryEnded() {
        uiState = uiState.copy(
            discoveryOngoing = false
        )
    }

    fun deviceDiscovered(device: BluetoothDevice) {
        if (uiState.deviceList.none { it.address == device.address }) {
            uiState = uiState.copy(
                deviceList = uiState.deviceList + device
            )
        }
    }
}