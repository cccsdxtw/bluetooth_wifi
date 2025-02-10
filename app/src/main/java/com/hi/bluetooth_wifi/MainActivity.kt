package com.hi.bluetooth_wifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hi.bluetooth_wifi.ui.bluetooth.BluetoothPage
import com.hi.bluetooth_wifi.ui.main.MainPage
import com.hi.bluetooth_wifi.ui.wifi.WifiPage
import com.hi.bluetooth_wifi.ui.wifi.WifiViewModel


@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainPage(navController) }
        composable("wifi") {  val wifiViewModel: WifiViewModel = viewModel()
            WifiPage(wifiViewModel = wifiViewModel,navController = navController)  }
        composable("bluetooth") { BluetoothPage() }

    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainApp() }
    }
}