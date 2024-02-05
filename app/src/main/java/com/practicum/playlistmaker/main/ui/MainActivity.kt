package com.practicum.playlistmaker.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.practicum.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.getViewModelFactory(this)
    }

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