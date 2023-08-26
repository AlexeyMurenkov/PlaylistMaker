package com.practicum.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOnClickListener = object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@MainActivity, (v as Button).text, Toast.LENGTH_SHORT).show()
            }
        }

        val buttonFind = findViewById<Button>(R.id.find)
        buttonFind.setOnClickListener(buttonOnClickListener)

        val buttonMedia = findViewById<Button>(R.id.media)
        buttonMedia.setOnClickListener{
            Toast.makeText(this@MainActivity, (it as Button).text, Toast.LENGTH_SHORT).show()
        }

        val buttonSettings = findViewById<Button>(R.id.settings)
        buttonSettings.setOnClickListener(buttonOnClickListener)
    }
}