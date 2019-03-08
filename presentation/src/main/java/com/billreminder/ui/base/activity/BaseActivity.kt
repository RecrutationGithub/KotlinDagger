package com.billreminder.ui.base.activity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    private val connectivityBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (isOnline) {
                onOnlineModeEnabled()
            } else {
                onOfflineModeEnabled()
            }
        }
    }

    protected val isOnline: Boolean
        get() {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo

            return netInfo != null && netInfo.isConnected
        }

    protected open fun onOnlineModeEnabled() {}

    protected open fun onOfflineModeEnabled() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(connectivityBroadcastReceiver, IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        unregisterReceiver(connectivityBroadcastReceiver)
        super.onDestroy()
    }
}