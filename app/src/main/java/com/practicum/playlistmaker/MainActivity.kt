package com.practicum.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonFind = findViewById<Button>(R.id.find)
        buttonFind.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        }

        val buttonMedia = findViewById<Button>(R.id.media)
        buttonMedia.setOnClickListener{
            startActivity(Intent(this, MediaActivity::class.java))
        }

        val buttonSettings = findViewById<Button>(R.id.settings)
        buttonSettings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}