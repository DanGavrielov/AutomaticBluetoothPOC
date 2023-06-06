package com.example.automaticbluetoothpoc.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScanButton(
    modifier: Modifier = Modifier,
    discoveryOngoing: Boolean,
    onDiscoveryStarted: () -> Unit,
    onDiscoveryEnded: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            if (discoveryOngoing) {
                onDiscoveryEnded()
            } else {
                onDiscoveryStarted()
            }
        }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = if (discoveryOngoing) "Stop Scan" else "Start Scan")
            if (discoveryOngoing) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = LocalContentColor.current,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}