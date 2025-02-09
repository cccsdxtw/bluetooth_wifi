package com.hi.bluetooth_wifi.ui.wifi


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import androidx.compose.runtime.setValue

@Composable
fun WifiPage() {
    // 初始化時間狀態
    var currentTime by remember { mutableStateOf(getCurrentTime()) }

    // 每秒更新時間
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            currentTime = getCurrentTime()
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        BasicText(text = "現在時間：$currentTime", modifier = Modifier.padding(bottom = 16.dp))
        LazyColumn {
            items(10) { index ->
                BasicText("WiFi $index")
            }
        }
    }
}

// 獲取當前時間的輔助函數
fun getCurrentTime(): String {
    val currentTime = System.currentTimeMillis()
    return SimpleDateFormat("HH:mm:ss").format(Date(currentTime))
}