package com.practicum.playlistmaker.media.ui

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practicum.playlistmaker.media.ui.fragments.FavoritesFragment
import com.practicum.playlistmaker.media.ui.fragments.PlaylistsFragment

class MediaViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = TABS_COUNT
    override fun createFragment(position: Int) = when (position) {
        0 -> FavoritesFragment.newInstance()
        else -> PlaylistsFragment.newInstance()
    }

    companion object {
        private const val TABS_COUNT = 2
    }
}
