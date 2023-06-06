package com.example.automaticbluetoothpoc.viewModel

import android.bluetooth.BluetoothDevice

data class UIState(
    val deviceList: List<BluetoothDevice> = emptyList(),
    val discoveryOngoing: Boolean = false
)