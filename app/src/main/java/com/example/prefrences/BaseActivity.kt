package com.example.prefrences

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager

abstract class BaseActivity:AppCompatActivity() {


    companion object {
        var isFragmentVisible = false
        var sManager: FragmentManager? = null
        var fragment = InternetBottomSheetDialog()
    }

    open fun adjustFontScale(configuration: Configuration, scale: Float) {
        configuration.fontScale = scale
        val metrics = resources.displayMetrics
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }

    private var mNetworkReceiver: BroadcastReceiver? = null
    private var networkReceiver: NetworkReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sManager = supportFragmentManager

        Handler(Looper.getMainLooper()).postDelayed({

            mNetworkReceiver = NetworkChangeReceiver()
            registerReceiver(
                mNetworkReceiver,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            )
            networkReceiver = NetworkReceiver()
            networkReceiver.let {
                if (it != null) {
                    LocalBroadcastManager.getInstance(baseContext).registerReceiver(
                        it,
                        IntentFilter("NETWORK")
                    )
                }
            }

        }, 0)

        adjustFontScale(resources.configuration, 1.0f);
//        setLocale()

    }

    /*private fun setLocale() {
        val resources: Resources = resources
        val configuration: Configuration = resources.getConfiguration()
        val locale: Locale = getLocale()
        if (!configuration.locale.equals(locale)) {
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, null)
        }
    }

    fun getLocale(): Locale {
        val newLocale: Locale = if (AppPreferencesDelegates.get().language == "en-US") {
            Locale("en")
        } else {
            Locale("ro")
        }
        return newLocale
    }*/

    override fun onDestroy() {
        super.onDestroy()
        if (mNetworkReceiver != null)
            unregisterReceiver(mNetworkReceiver)
//        DeviceUtils.dismissProgress()
    }

    private class NetworkReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val status = intent?.getStringExtra("STATUS")
            if (status.equals("OFF")) {
                if (!isFragmentVisible) {
                    try {
                        sManager?.let { fragment.show(it, "no_internet") }
                        isFragmentVisible = true
                    } catch (e: Exception) {
                    }
                }
            } else {
                if (isFragmentVisible) {
                    fragment.dismiss()
                    isFragmentVisible = false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        System.gc()
    }

}