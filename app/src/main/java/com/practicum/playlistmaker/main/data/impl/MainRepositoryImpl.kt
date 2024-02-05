package com.practicum.playlistmaker.main.data.impl

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker.main.data.MainRepository
import com.practicum.playlistmaker.media.ui.MediaActivity
import com.practicum.playlistmaker.search.ui.SearchActivity
import com.practicum.playlistmaker.settings.ui.SettingsActivity

class MainRepositoryImpl(private val context: Context) : MainRepository {
    override fun openSearchActivity() {
        context.startActivity(Intent(context, SearchActivity::class.java))
    }

    override fun openMediaActivity() {
        context.startActivity(Intent(context, MediaActivity::class.java))
    }

    override fun openSettingsActivity() {
        context.startActivity(Intent(context, SettingsActivity::class.java))
    }
}