package com.practicum.playlistmaker.media.ui

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.practicum.playlistmaker.media.ui.fragments.FavoritesFragment
import com.practicum.playlistmaker.media.ui.fragments.PlaylistsFragment

class MediaViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = MEDIA_TABS.size
    override fun createFragment(position: Int) = MEDIA_TABS[position]

    companion object {
        private val MEDIA_TABS =
            listOf(FavoritesFragment(), PlaylistsFragment())
    }
}
