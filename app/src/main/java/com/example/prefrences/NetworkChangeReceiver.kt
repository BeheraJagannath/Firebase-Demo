package com.example.prefrences

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo

        val lbm1 = context.let { LocalBroadcastManager.getInstance(it) }
        val localIn1 = Intent("NETWORK")
//        Log.e("activeNetworkInfo:", activeNetworkInfo?.isConnected.toString())
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            localIn1.putExtra("STATUS", "ON")
        } else {
            localIn1.putExtra("STATUS", "OFF")
        }
        lbm1.sendBroadcast(localIn1)
    }
}