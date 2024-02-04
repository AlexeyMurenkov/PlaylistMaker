package com.practicum.playlistmaker.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        val viewModel = ViewModelProvider(
            this,
            MainViewModel.getViewModelFactory(this)
        )[MainViewModel::class.java]


        with(binding) {
            setContentView(root)

            mainSearch.setOnClickListener { viewModel.openSearchActivity() }
            mainMedia.setOnClickListener { viewModel.openMediaActivity() }
            mainSettings.setOnClickListener { viewModel.openSettingsActivity() }
        }
    }
}