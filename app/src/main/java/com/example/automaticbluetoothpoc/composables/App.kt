package com.example.automaticbluetoothpoc.composables

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun App(
    modifier: Modifier = Modifier,
    deviceList: List<BluetoothDevice>,
    discoveryOngoing: Boolean,
    onDiscoveryStarted: () -> Unit,
    onDiscoveryEnded: () -> Unit,
    onPermissionsGranted: () -> Unit
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = buildList {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                add(Manifest.permission.BLUETOOTH_SCAN)
                add(Manifest.permission.BLUETOOTH_CONNECT)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.NEARBY_WIFI_DEVICES)
            }
        }
    )

    if (permissionState.allPermissionsGranted) {
        DiscoveredDevicesListScreen(
            deviceList = deviceList,
            discoveryOngoing = discoveryOngoing,
            onDiscoveryStarted = onDiscoveryStarted,
            onDiscoveryEnded = onDiscoveryEnded
        ).also { onPermissionsGranted() }
    } else {
        MissingPermissionsScreen(
            modifier = modifier,
            permissionState = permissionState
        )
    }
}