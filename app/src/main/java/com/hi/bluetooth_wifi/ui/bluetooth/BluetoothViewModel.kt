package com.hi.bluetooth_wifi.ui.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BluetoothViewModel : ViewModel() {
    val _bluetoothList = MutableStateFlow<List<String>>(emptyList())
    val bluetoothList: StateFlow<List<String>> = _bluetoothList

    private val _isScanning = MutableStateFlow(false) // 新增 isScanning 狀態
    val isScanning: StateFlow<Boolean> = _isScanning

    private val adapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    @SuppressLint("MissingPermission")
    fun startBluetoothScan(context: Context) {
        if (adapter == null) {
            _bluetoothList.value = listOf("設備不支援藍牙")
            _isScanning.value = false
            return
        }

        if (!adapter.isEnabled) {
            _bluetoothList.value = listOf("藍牙未啟用")
            _isScanning.value = false
            return
        }

        _bluetoothList.value = emptyList() // 清空設備列表
        _isScanning.value = true // 開始掃描時設為 true

        if (adapter.isDiscovering) {
            adapter.cancelDiscovery()
        }

        adapter.startDiscovery()

        // 註冊廣播接收器來處理發現的藍牙設備
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (BluetoothDevice.ACTION_FOUND == action) {
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name ?: "未知設備"
                    val deviceAddress = device?.address

                    _bluetoothList.value = _bluetoothList.value + "$deviceName ($deviceAddress)"
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action) {
                    _isScanning.value = false // 掃描結束時設為 false
                }
            }
        }

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }
        context.registerReceiver(receiver, filter)
    }

    fun clearBluetoothList() {
        _bluetoothList.value = emptyList() // 清空藍牙設備列表
    }
}
