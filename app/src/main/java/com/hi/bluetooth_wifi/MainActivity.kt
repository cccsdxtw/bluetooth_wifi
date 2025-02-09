package com.hi.bluetooth_wifi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hi.bluetooth_wifi.ui.bluetooth.BluetoothPage
import com.hi.bluetooth_wifi.ui.main.MainPage
import com.hi.bluetooth_wifi.ui.wifi.WifiPage


@Composable
fun MainApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainPage(navController) }
        composable("wifi") { WifiPage() }
        composable("bluetooth") { BluetoothPage() }

    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainApp() }
    }
}