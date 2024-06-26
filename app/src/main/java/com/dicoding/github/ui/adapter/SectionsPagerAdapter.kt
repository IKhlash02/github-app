package com.dicoding.github.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.github.ui.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) :FragmentStateAdapter(activity) {

    var username: String = "a"
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowingFragment.ARG_SECTION_NUMBER, position + 1)
            putString(FollowingFragment.ARG_USERNAME, username)
        }

        return fragment

    }
}