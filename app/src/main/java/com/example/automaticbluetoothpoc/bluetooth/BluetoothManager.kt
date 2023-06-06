package com.example.automaticbluetoothpoc.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

@SuppressLint("MissingPermission")
class BluetoothManager(
    private val context: Context,
    private val receiver: DeviceDiscoveredBroadcastReceiver
) {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothInitialized = false

    fun init() {
        if (!bluetoothInitialized) {
            bluetoothInitialized = true

            initBluetoothAdapter()
            ensureBluetoothIsTurnedOn()
            registerDeviceDiscoveryReceiver(receiver)
        }
    }

    fun destroy() {
        context.unregisterReceiver(receiver)
    }

    fun startDeviceDiscovery(onDiscoveryStarted: () -> Unit) {
        bluetoothAdapter?.let {
            if (it.isDiscovering) {
                it.cancelDiscovery()
            }

            it.startDiscovery()
            onDiscoveryStarted()
        }
    }

    fun stopDeviceDiscovery(onDiscoveryStopped: () -> Unit) {
        bluetoothAdapter?.let {
            if (it.isDiscovering) {
                it.cancelDiscovery()
                onDiscoveryStopped()
            }
        }
    }

    private fun ensureBluetoothIsTurnedOn() = bluetoothAdapter?.let {
        if (!it.isEnabled) {
            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE).apply {
                context.startActivity(this)
            }
        }
    }

    private fun initBluetoothAdapter() {
        val bluetoothManager = context.getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter
    }

    private fun registerDeviceDiscoveryReceiver(receiver: DeviceDiscoveredBroadcastReceiver) {
        listOf(
            IntentFilter(BluetoothDevice.ACTION_FOUND),
            IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        ).forEach { context.registerReceiver(receiver, it) }
    }
}