package com.practicum.playlistmaker.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.activity.SettingsActivity.Companion.DARK_THEME
import com.practicum.playlistmaker.activity.SettingsActivity.Companion.SETTINGS_PREFERENCES
import com.practicum.playlistmaker.databinding.ActivityMainBinding
import com.practicum.playlistmaker.utils.switchTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            mainSearch.setOnClickListener {
                startActivity(Intent(it.context, SearchActivity::class.java))
            }
            mainMedia.setOnClickListener {
                startActivity(Intent(it.context, MediaActivity::class.java))
            }
            mainSettings.setOnClickListener {
                startActivity(Intent(it.context, SettingsActivity::class.java))
            }

            switchTheme(
                getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE)
                    .getBoolean(
                        DARK_THEME,
                        AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
                    )
            )
        }
    }
}