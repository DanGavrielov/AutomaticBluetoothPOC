package com.example.automaticbluetoothpoc.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class DeviceDiscoveredBroadcastReceiver(
    private val onDeviceDiscovered: (BluetoothDevice) -> Unit,
    private val onDiscoveryStopped: () -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            BluetoothDevice.ACTION_FOUND -> {
                val device: BluetoothDevice? =
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
                        @Suppress("DEPRECATION")
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    } else {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            BluetoothDevice::class.java
                        )
                    }
                device?.let { onDeviceDiscovered(it) }
            }

            BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                onDiscoveryStopped()
            }
        }
    }
}