package com.practicum.playlistmaker.settings.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySettingsBinding.inflate(layoutInflater)

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