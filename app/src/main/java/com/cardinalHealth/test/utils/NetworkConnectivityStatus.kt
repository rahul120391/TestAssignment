package com.cardinalHealth.test.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkConnectivityStatus private constructor() {
    companion object {
        private val instance = NetworkConnectivityStatus()
        var context: Context? = null
        @Synchronized
        fun getInstance(context: Context): NetworkConnectivityStatus {
            this.context = context
            return instance
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}