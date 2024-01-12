package com.example.loginfirebasedemo

import android.app.PendingIntent.getActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.Navigation.NavigationActivity
import com.example.prefrences.AppPreferencesDelegates

class SplashActivity : AppCompatActivity() {
    private val appPreferences = AppPreferencesDelegates.get()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            if (appPreferences.wasOnboardingScreen) {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()

            } else {
                val i = Intent(this, NavigationActivity::class.java)
                startActivity(i)
                finish()
            }
        }, 200)






    }

}