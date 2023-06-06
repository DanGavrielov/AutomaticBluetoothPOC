package com.example.automaticbluetoothpoc.composables

import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DiscoveredDevicesListScreen(
    modifier: Modifier = Modifier,
    deviceList: List<BluetoothDevice>,
    discoveryOngoing: Boolean,
    onDiscoveryStarted: () -> Unit,
    onDiscoveryEnded: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvailableDevicesList(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            deviceList = deviceList
        )

        ScanButton(
            discoveryOngoing = discoveryOngoing,
            onDiscoveryStarted = onDiscoveryStarted,
            onDiscoveryEnded = onDiscoveryEnded
        )
    }
}