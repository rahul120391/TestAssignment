package com.cardinalHealth.test.utils

import android.content.Context
import android.widget.Toast
import com.cardinalHealth.test.R

open class Utility private constructor() {


    companion object {
        var context: Context? = null
        private val instance = Utility()

        @Synchronized
        fun getInstance(context: Context): Utility {
            this.context = context
            return instance
        }
    }

    fun checkNetworkConnectivity():Boolean=
        context?.let { NetworkConnectivityStatus.getInstance(it).isNetworkAvailable() }?:false

    fun showErrorMessage():String= context?.getString(R.string.errorWhileFetchingData)?:""

    fun showNoInternetError(){
        context?.getString(R.string.noInternetAvailable)?.let {
            Toast.makeText(context,it, Toast.LENGTH_SHORT).show()
        }
    }
}