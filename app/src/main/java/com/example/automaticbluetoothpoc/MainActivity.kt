package com.example.automaticbluetoothpoc

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.automaticbluetoothpoc.bluetooth.BluetoothManager
import com.example.automaticbluetoothpoc.bluetooth.DeviceDiscoveredBroadcastReceiver
import com.example.automaticbluetoothpoc.composables.App
import com.example.automaticbluetoothpoc.viewModel.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@SuppressLint("MissingPermission")
class MainActivity : ComponentActivity() {
    private lateinit var bluetoothManager: BluetoothManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            bluetoothManager = BluetoothManager(
                context = LocalContext.current,
                receiver = DeviceDiscoveredBroadcastReceiver(
                    onDeviceDiscovered = viewModel::deviceDiscovered,
                    onDiscoveryStopped = viewModel::discoveryEnded
                )
            )
            App(
                modifier = Modifier.fillMaxSize(),
                deviceList = viewModel.uiState.deviceList,
                discoveryOngoing = viewModel.uiState.discoveryOngoing,
                onDiscoveryStarted = {
                    bluetoothManager.startDeviceDiscovery {
                        viewModel.discoveryStarted()
                    }
                },
                onDiscoveryEnded = {
                    bluetoothManager.stopDeviceDiscovery {
                        viewModel.discoveryEnded()
                    }
                },
                onPermissionsGranted = {
                    bluetoothManager.init()
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        bluetoothManager.destroy()
    }
}