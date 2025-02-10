package com.hi.bluetooth_wifi.ui.wifi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController


@Composable
fun WifiPage(wifiViewModel: WifiViewModel,navController: NavController) {
    val wifiList by wifiViewModel.wifiList.collectAsState()
    val loading by wifiViewModel.isLoading.collectAsState()
    val errorMessage by wifiViewModel.errorMessage.collectAsState()
    val context = LocalContext.current

    // 觸發初次掃描
    LaunchedEffect(Unit) {
        wifiViewModel.fetchWifiList()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 顯示載入狀態
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp))
        } else {
            Text("Wi-Fi 設備列表", modifier = Modifier.padding(bottom = 16.dp))
        }

        // 顯示錯誤訊息
        errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(bottom = 16.dp))
        }

        // 顯示 Wi-Fi 設備
        if (wifiList.isEmpty() && !loading) {
            Text("沒有找到 Wi-Fi 設備", modifier = Modifier.padding(bottom = 16.dp))
        } else {
            LazyColumn {
                items(wifiList.size) { wifi ->
                    Text(wifiList[wifi], modifier = Modifier.padding(8.dp))
                }
            }
        }

        // 重新掃描按鈕
        Button(onClick = { wifiViewModel.fetchWifiList() }) {
            Text("重新掃描")
        }

        // 返回按鈕
        Button(onClick = {
            // 這裡可以進行返回邏輯，像是導航回上一頁
            navController.popBackStack()
        }) {
            Text("返回")
        }
    }
}
