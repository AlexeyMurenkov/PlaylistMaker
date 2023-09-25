package com.practicum.playlistmaker.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practicum.playlistmaker.databinding.ActivityMainBinding

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
        }
    }
}