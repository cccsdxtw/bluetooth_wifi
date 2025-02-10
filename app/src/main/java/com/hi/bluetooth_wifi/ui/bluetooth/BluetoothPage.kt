package com.hi.bluetooth_wifi.ui.bluetooth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun BluetoothPage(viewModel: BluetoothViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), navController: NavController) {
    val context = LocalContext.current
    val bluetoothList by viewModel.bluetoothList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startBluetoothScan(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { viewModel.startBluetoothScan(context) }) {
            Text("開始掃描藍牙設備")
        }

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(bluetoothList) { device ->
                Text( text = device )
            }
        }

        // 返回按鈕
        Button(onClick = { navController.popBackStack() }) {
            Text("返回")
        }
    }
}
