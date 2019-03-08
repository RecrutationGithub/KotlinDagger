package com.billreminder.ui.base.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    private val connectivityBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) =
            if (isOnline) {
                onOnlineModeEnabled()
            } else {
                onOfflineModeEnabled()
            }
    }

    protected val isOnline: Boolean
        get() {
            val activity = activity ?: return false
            val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo

            return netInfo != null && netInfo.isConnected
        }

    protected open fun onOnlineModeEnabled() {}

    protected open fun onOfflineModeEnabled() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activity = activity
        activity?.registerReceiver(connectivityBroadcastReceiver, IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onDestroy() {
        activity?.unregisterReceiver(connectivityBroadcastReceiver)
        super.onDestroy()
    }
}