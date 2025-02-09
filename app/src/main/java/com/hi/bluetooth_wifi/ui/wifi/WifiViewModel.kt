package com.hi.bluetooth_wifi.ui.wifi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WifiViewModel : ViewModel() {
    private val _wifiList = MutableStateFlow<List<String>>(emptyList())
    val wifiList: StateFlow<List<String>> = _wifiList

    init {
        fetchWifiList()
    }

    private fun fetchWifiList() {
        viewModelScope.launch {
            // 模擬抓取 WiFi 資料的過程
            delay(1000) // 假設 API 請求需要一秒
            _wifiList.value = listOf("WiFi 1", "WiFi 2", "WiFi 3", "WiFi 4",  "WiFi 4",  "WiFi 4",  "WiFi 4",  "WiFi 4",  "WiFi 4")
        }
    }
}
