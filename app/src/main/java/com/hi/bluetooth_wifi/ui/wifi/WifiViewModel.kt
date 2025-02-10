package com.hi.bluetooth_wifi.ui.wifi

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class WifiViewModel(application: Application) : AndroidViewModel(application) {
    private val wifiManager = application.getSystemService(WifiManager::class.java)

    private val _wifiList = MutableStateFlow<List<String>>(emptyList())
    open var wifiList: StateFlow<List<String>> = _wifiList

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    open val errorMessage: StateFlow<String?> = _errorMessage

    // 新增一個控制是否顯示假資料的變數
    open var previewMode: Boolean = false

    private val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.value = false // 停止載入

                // 檢查權限
                if (ContextCompat.checkSelfPermission(
                        application,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) {
                    val results: List<ScanResult> = wifiManager.scanResults
                    _wifiList.value = results.map { it.SSID }.filter { it.isNotEmpty() }
                } else {
                    _errorMessage.value = "沒有存取 WiFi 的權限"
                }
            }
        }
    }

    init {
        // 註冊掃描結果接收器
        val intentFilter = IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        application.registerReceiver(wifiScanReceiver, intentFilter)
    }

    // 開始 Wi-Fi 掃描
    fun fetchWifiList() {
        if (previewMode) {
            // 顯示假資料
            _wifiList.value = listOf("Wifi Network 1", "Wifi Network 2", "Wifi Network 3")
            _isLoading.value = false
        } else {
            // 檢查權限
            if (ContextCompat.checkSelfPermission(
                    getApplication(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                _isLoading.value = true // 開始載入
                wifiManager.startScan()
            } else {
                _errorMessage.value = "請求權限失敗"
            }
        }
    }

    // 手動刷新
    fun refreshWifiList() {
        fetchWifiList()
    }

    override fun onCleared() {
        super.onCleared()
        // 記得取消註冊接收器
        getApplication<Application>().unregisterReceiver(wifiScanReceiver)
    }
}
