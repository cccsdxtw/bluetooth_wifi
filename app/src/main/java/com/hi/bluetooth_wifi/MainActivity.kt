package com.hi.bluetooth_wifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hi.bluetooth_wifi.theme.BackgroundDark
import com.hi.bluetooth_wifi.theme.MyAppTheme
import com.hi.bluetooth_wifi.theme.BackgroundLight
import com.hi.bluetooth_wifi.ui.bluetooth.BluetoothPage
import com.hi.bluetooth_wifi.ui.bluetooth.BluetoothViewModel
import com.hi.bluetooth_wifi.ui.main.MainPage
import com.hi.bluetooth_wifi.ui.wifi.WifiPage
import com.hi.bluetooth_wifi.ui.wifi.WifiViewModel


@Composable
fun MainApp() {
    val navController = rememberNavController()
    // 根據系統主題決定背景顏色
    val backgroundColor = if (isSystemInDarkTheme()) {
        BackgroundDark
    } else {
        BackgroundLight
    }
    Box(modifier = Modifier.fillMaxSize()) {
        // 設置背景顏色，隨著深色模式變化
             Box(
            modifier = Modifier.fillMaxSize().background(backgroundColor) // 設定背景顏色
        )
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                MainPage(navController)
            }
            composable("wifi") {
                val wifiViewModel: WifiViewModel = viewModel()
                WifiPage(wifiViewModel = wifiViewModel, navController = navController)
            }
            composable("bluetooth") {
                val viewModel: BluetoothViewModel = viewModel()
                BluetoothPage(viewModel = viewModel, navController = navController)
            }

        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyAppTheme { MainApp() } }
    }
}