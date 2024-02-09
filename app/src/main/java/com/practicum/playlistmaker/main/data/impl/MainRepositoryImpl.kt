package com.practicum.playlistmaker.main.data.impl

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker.main.domain.MainRepository
import com.practicum.playlistmaker.media.ui.MediaActivity
import com.practicum.playlistmaker.search.ui.SearchActivity
import com.practicum.playlistmaker.settings.ui.SettingsActivity

class MainRepositoryImpl(private val context: Context) : MainRepository {
    override fun openSearchActivity() {
        context.startActivity(Intent(context, SearchActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun openMediaActivity() {
        context.startActivity(Intent(context, MediaActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    override fun openSettingsActivity() {
        context.startActivity(Intent(context, SettingsActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}