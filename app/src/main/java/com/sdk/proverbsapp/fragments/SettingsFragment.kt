package com.sdk.proverbsapp.fragments

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.inihood.colorfultoast.ColorfulToast
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.databinding.FragmentSettingsBinding
import com.sdk.proverbsapp.manager.SharedPrefManager
import com.sdk.proverbsapp.util.toast
import kotlinx.coroutines.delay

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private lateinit var sharedPrefManager: SharedPrefManager
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        admob()
    }

    private fun initViews() {
        sharedPrefManager = SharedPrefManager(requireContext())
        isCheck()
        binding.apply {
            btn1.setOnClickListener {
                toast(requireContext(), R.color.color1)
                sharedPrefManager.saveColor(0)
            }
            btn2.setOnClickListener {
                toast(requireContext(), R.color.color2)
                sharedPrefManager.saveColor(1)
            }
            btn3.setOnClickListener {
                toast(requireContext(), R.color.dark_blue)
                sharedPrefManager.saveColor(2)
            }
            btn4.setOnClickListener {
                toast(requireContext(), R.color.color4)
                sharedPrefManager.saveColor(3)

            }
            btn5.setOnClickListener {
                toast(requireContext(), R.color.light_gray3)
                sharedPrefManager.saveColor(4)
            }
        }

        checkNightMode()

        binding.switchBtn.setOnToggledListener { _, isOn ->
            if (isOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPrefManager.isDarkMode(true)
                isCheck()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPrefManager.isDarkMode(false)
                isCheck()
            }
        }
        binding.apply {
            linearSharing.setOnClickListener {
                shareThisApp()
            }
            linearRate.setOnClickListener {
                intentToPlayMarket()
            }
            linearTel.setOnClickListener {
                intentToTelegram()
            }
        }
    }

    private fun admob() {
        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()
        binding.admob.loadAd(adRequest)
    }

    private fun checkNightMode() {
        val currentNightMode: Int = requireContext().resources
            .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_YES -> {
                sharedPrefManager.isDarkMode(true)
                isCheck()
                binding.switchBtn.isOn = true
            }
            else -> {
                sharedPrefManager.isDarkMode(false)
                isCheck()
                binding.switchBtn.isOn = sharedPrefManager.getDarkMode()
            }
        }
    }

    private fun intentToTelegram() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://t.me/Android_Dev_Portolio")
        val intentChooser = Intent.createChooser(intent, "Launch Telegram")
        startActivity(intentChooser)
    }

    private fun intentToPlayMarket() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://play.google.com/store/apps/details?id=com.sdk.proverbsapp")
        val intentChooser = Intent.createChooser(intent, "Launch Play Market")
        startActivity(intentChooser)
    }

    private fun shareThisApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "https://play.google.com/store/apps/details?id=com.sdk.proverbsapp")
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun toast(context: Context, color: Int) {
        val colorful = ColorfulToast.Builder(context)
            .setBackgroundColor(ContextCompat.getColor(context, color))
            .setToastPosition(Gravity.BOTTOM)
            .setCornerRadius(50)

        colorful.show()
    }

    private fun isCheck() {
        if (sharedPrefManager.getDarkMode()) {
            binding.apply {
                textView1.setTextColor(Color.WHITE)
                textView2.setTextColor(Color.WHITE)
                textView3.setTextColor(Color.WHITE)
                textView4.setTextColor(Color.WHITE)
                textView5.setTextColor(Color.WHITE)
            }
        } else {
            binding.apply {
                textView1.setTextColor(Color.BLACK)
                textView2.setTextColor(Color.BLACK)
                textView3.setTextColor(Color.BLACK)
                textView4.setTextColor(Color.BLACK)
                textView5.setTextColor(Color.BLACK)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}