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
    private val _bluetoothList = MutableStateFlow<List<String>>(emptyList())
    val bluetoothList: StateFlow<List<String>> = _bluetoothList

    private val adapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    @SuppressLint("MissingPermission")
    fun startBluetoothScan(context: Context) {
        if (adapter == null) {
            _bluetoothList.value = listOf("設備不支援藍牙")
            return
        }

        if (!adapter.isEnabled) {
            _bluetoothList.value = listOf("藍牙未啟用")
            return
        }

        _bluetoothList.value = emptyList() // 清空設備列表

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
                }
            }
        }

        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
    }
}
