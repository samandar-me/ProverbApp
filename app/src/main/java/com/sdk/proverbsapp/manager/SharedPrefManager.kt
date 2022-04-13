package com.sdk.proverbsapp.manager

import android.content.Context
import com.sdk.proverbsapp.util.Utils.SHARED_PREF

class SharedPrefManager(context: Context) {

    private val pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    fun saveColor(color: Int) {
        val edit = pref.edit()
        edit.putInt("saveColor", color)
        edit.apply()
    }

    fun getSavedColor(): Int {
        return pref.getInt("saveColor", 0)
    }

    fun isDarkMode(isDarkMode: Boolean) {
        val edit = pref.edit()
        edit.putBoolean("isDark", isDarkMode)
        edit.apply()
    }

    fun getDarkMode(): Boolean {
        return pref.getBoolean("isDark", false)
    }
}