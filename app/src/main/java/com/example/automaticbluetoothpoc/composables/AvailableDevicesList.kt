package com.example.automaticbluetoothpoc.composables

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("MissingPermission")
@Composable
fun AvailableDevicesList(
    modifier: Modifier = Modifier,
    deviceList: List<BluetoothDevice>
) {
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(deviceList) {bluetoothDevice ->
            bluetoothDevice.name?.let {
                DiscoveredDevice(device = bluetoothDevice) { device ->
                    device.createBond()
                }
                Divider()
            }
        }
    }
}