package com.hi.bluetooth_wifi.ui.bluetooth

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BluetoothViewModel : ViewModel() {
    private val _bluetoothList = MutableStateFlow<List<String>>(emptyList())
    val bluetoothList: StateFlow<List<String>> = _bluetoothList

    init {
        fetchBluetoothDevices()
    }

    private fun fetchBluetoothDevices() {
        viewModelScope.launch {
            // 模擬抓取藍牙設備資料的過程
            delay(1000) // 假設 API 請求需要一秒
            _bluetoothList.value = listOf("Device 1","Device 1","Device 1","Device 1")

        }
    }
}

// 模擬藍牙設備類別
data class MockBluetoothDevice(val name: String)
