package com.sdk.proverbsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sdk.proverbsapp.fragments.FavoriteFragment
import com.sdk.proverbsapp.fragments.ProverbsFragment
import com.sdk.proverbsapp.fragments.SettingsFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProverbsFragment()
            1 -> FavoriteFragment()
            2 -> SettingsFragment()
            else -> Fragment()
        }
    }
}