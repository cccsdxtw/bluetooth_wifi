package com.hi.bluetooth_wifi.ui.wifi


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

import androidx.compose.runtime.setValue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WifiPage(viewModel: WifiViewModel = viewModel()) {
    // 初始化更新時間狀態
    var currentTime by remember { mutableStateOf(getCurrentTime()) }

    // 每秒更新時間
    LaunchedEffect(Unit) {
        while (true) {
            delay(10000)
            currentTime = getCurrentTime()
        }
    }

    // 觀察 ViewModel 中的 WiFi 列表
    val wifiList by viewModel.wifiList.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 顯示更新時間
        BasicText(text = "更新時間：$currentTime", modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn {
            items(wifiList.size) { index ->
                BasicText(wifiList[index])
            }
        }
    }
}
fun getCurrentTime(): String {
    val currentTime = System.currentTimeMillis()
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(currentTime))
}