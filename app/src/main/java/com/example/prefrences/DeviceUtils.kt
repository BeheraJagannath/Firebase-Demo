package com.example.prefrences

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.InputMethodManager

object DeviceUtils {

    var progressbar: Progressbar? = null

    fun hideKeyboard(context: Context) {
        try {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            (context as? Activity)?.currentFocus?.windowToken.let {
                inputManager.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initProgress(context: Context){
//        progressbar = Progressbar(context)
    }

    fun showProgress(){
//        progressbar?.showPopup()
    }

    fun dismissProgress(){
//        progressbar?.dismissPopup()
    }
    fun isOnline(app: Application): Boolean {
        val connMgr = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }

}