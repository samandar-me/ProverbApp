package com.sdk.proverbsapp.activity

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.manager.SharedPrefManager

class SplashActivity : AppCompatActivity() {
    private lateinit var sharedPrefManager: SharedPrefManager
    var bool = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initViews()
        isNightMode()

    }

    private fun initViews() {
        sharedPrefManager = SharedPrefManager(this)
        val countDownTimer = object : CountDownTimer(600, 1000) {
            override fun onTick(p0: Long) {}

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
        countDownTimer.start()
    }


    private fun isNightMode() {
        val currentNightMode: Int = applicationContext.resources
            .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                if (bool) {
                    sharedPrefManager.isDarkMode(true)
                    Toast.makeText(
                        this,
                        sharedPrefManager.getDarkMode().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    bool = true
                }
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                if (sharedPrefManager.getDarkMode()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    sharedPrefManager.isDarkMode(true)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    sharedPrefManager.isDarkMode(false)
                }
            }
        }
    }
}

