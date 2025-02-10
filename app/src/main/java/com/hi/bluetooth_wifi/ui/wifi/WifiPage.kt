package com.hi.bluetooth_wifi.ui.wifi

import android.app.Application
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.hi.bluetooth_wifi.theme.BackgroundDark
import com.hi.bluetooth_wifi.theme.BackgroundLight
import com.hi.bluetooth_wifi.theme.TestDark
import com.hi.bluetooth_wifi.theme.TestLight
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun WifiPage(wifiViewModel: WifiViewModel? = null, navController: NavController) {
    val wifiList = if (wifiViewModel == null) {
        listOf("Wifi Network 1", "Wifi Network 2", "Wifi Network 3") // 顯示假資料
    } else {
        // 觸發初次掃描
        LaunchedEffect(Unit) {
            wifiViewModel.fetchWifiList()
        }
        wifiViewModel.wifiList.collectAsState().value // 顯示真實資料
    }

    val loading = wifiViewModel?.isLoading?.collectAsState()?.value ?: false
    val errorMessage = wifiViewModel?.errorMessage?.collectAsState()?.value

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Button(onClick = {
            wifiViewModel?.clearWifiList() // 清空列表
            wifiViewModel?.fetchWifiList() // 重新掃描
             }) {
            Text("重新掃描")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // 返回按鈕
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("返回")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWifiPage() {
    val navController = rememberNavController()

    // 這裡不使用 ViewModel，直接顯示假資料
    WifiPage(navController = navController)
}
