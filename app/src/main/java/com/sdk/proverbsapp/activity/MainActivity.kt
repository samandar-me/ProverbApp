package com.sdk.proverbsapp.activity

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.fragments.DetailFragment
import com.sdk.proverbsapp.manager.SharedPrefManager
import com.sdk.proverbsapp.util.toast
import kotlinx.coroutines.flow.MutableStateFlow
import java.security.AccessController.getContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}