package com.practicum.playlistmaker.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySettingsBinding.inflate(layoutInflater)

        val viewModel = ViewModelProvider(
            this,
            SettingsViewModel.getViewModelFactory(this)
        )[SettingsViewModel::class.java]

        with(binding) {
            setContentView(root)

            settingsBack.setOnClickListener {
                finish()
            }
            settingsShareApp.setOnClickListener {
                viewModel.shareApp()
            }
            settingsAgreement.setOnClickListener {
                viewModel.showAgreement()
            }
            settingsSupport.setOnClickListener {
                viewModel.mailToSupport()
            }
            settingsDarkTheme.setOnCheckedChangeListener { _, checked ->
                viewModel.switchTheme(checked)
            }
        }

        viewModel.getStateIsDarkTheme().observe(this) {
            binding.settingsDarkTheme.isChecked = it
        }
    }
}