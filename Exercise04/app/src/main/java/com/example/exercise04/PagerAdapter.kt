package com.example.exercise04

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FcbFragment()
            1 -> TshirtFragment()
            2 -> StadiumFragment()
            else -> FcbFragment()
        }
    }
}
