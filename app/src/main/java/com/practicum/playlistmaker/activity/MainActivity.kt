package com.practicum.playlistmaker.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practicum.playlistmaker.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonFind = findViewById<Button>(R.id.activity_main_search)
        buttonFind.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        val buttonMedia = findViewById<Button>(R.id.activity_main_media)
        buttonMedia.setOnClickListener{
            startActivity(Intent(this, MediaActivity::class.java))
        }

        val buttonSettings = findViewById<Button>(R.id.activity_main_settings)
        buttonSettings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}