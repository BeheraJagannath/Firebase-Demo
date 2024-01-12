package com.example.traintraking.prefrences

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.multidex.MultiDexApplication
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.pixplicity.easyprefs.library.Prefs
import dagger.hilt.android.HiltAndroidApp

//class PrefrencesApplicaton : Application() {
//    override fun onCreate() {
//        super.onCreate()
//
//        Prefs.Builder()
//            .setContext(this)
//            .setMode(ContextWrapper.MODE_PRIVATE)
//            .setPrefsName(packageName)
//            .setUseDefaultSharedPreference(true)
//            .build()
//
//    }
//}
@HiltAndroidApp
class PrefrencesApplicaton : MultiDexApplication() {
    companion object {
        lateinit var appInstance: Application
    }
    override fun onCreate() {
        super.onCreate()
        Prefs.Builder()
            .setContext(this)
            .setMode(MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()
        appInstance = this

        registerReceiver(
            TransferNetworkLossHandler.getInstance(applicationContext), IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
        )

    }

}