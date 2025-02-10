package com.hi.bluetooth_wifi.ui.bluetooth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hi.bluetooth_wifi.ui.wifi.WifiPage
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun BluetoothPage(viewModel: BluetoothViewModel? = null, navController: NavController) {
    val context = LocalContext.current
    val bluetoothList by (viewModel?.bluetoothList ?: MutableStateFlow(listOf("請稍候"))).collectAsState()
    val isScanning by (viewModel?.isScanning ?: MutableStateFlow(false)).collectAsState() // 偵測是否正在掃描

    if (viewModel != null) {
        LaunchedEffect(Unit) {
            viewModel.startBluetoothScan(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 顯示讀取圈圈當正在掃描
        if (isScanning) {
            CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp))
        }

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(bluetoothList) { device ->
                Text(text = device)
            }
        }

        Button(onClick = {
            viewModel?.clearBluetoothList()  // 清空設備列表
            viewModel?.startBluetoothScan(context)  // 開始新的掃描
        }) {
            Text("重新掃描")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("返回")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBluetoothPage() {
    val navController = rememberNavController()

    BluetoothPage(navController = navController)
}