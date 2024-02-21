package com.practicum.playlistmaker.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            mainSearch.setOnClickListener { viewModel.openSearchActivity() }
            mainMedia.setOnClickListener { viewModel.openMediaActivity() }
            mainSettings.setOnClickListener { viewModel.openSettingsActivity() }
        }
    }
}