package com.practicum.playlistmaker.media.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityMediaBinding

class MediaActivity : AppCompatActivity() {

    private lateinit var tabMediator: TabLayoutMediator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMediaBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            mediaBack.setOnClickListener { finish() }
            viewPager.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle)

            tabMediator = TabLayoutMediator(tabs, viewPager) { tab, pos ->
                when (pos) {
                    0 -> tab.text = getString(R.string.media_tab_favorites)
                    1 -> tab.text = getString(R.string.media_tab_playlists)
                }
            }
        }
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }
}
