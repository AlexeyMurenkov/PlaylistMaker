package com.practicum.playlistmaker.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding
import com.practicum.playlistmaker.utils.switchTheme

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            settingsBack.setOnClickListener {
                finish()
            }
            settingsShareApp.setOnClickListener {
                shareApp()
            }
            settingsSupport.setOnClickListener {
                mailToSupport()
            }
            settingsAgreement.setOnClickListener {
                showAgreement()
            }
            settingsDarkTheme.isChecked =
                AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            settingsDarkTheme.setOnCheckedChangeListener { _, checked ->
                switchTheme(checked)
                getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE)
                    .edit()
                    .putBoolean(DARK_THEME, checked)
                    .apply()
            }
        }
    }

    private fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text))
            .type = "text/plain"
        startActivity(intent)
    }

    private fun mailToSupport() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent
            .setData(Uri.parse("mailto:"))
            .putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_mail)))
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_default_subject))
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.support_default_text))
        startActivity(intent)
    }

    private fun showAgreement() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent
            .setData(Uri.parse(getString(R.string.agreement)))
        startActivity(intent)
    }

    companion object {
        const val SETTINGS_PREFERENCES = "settings_preferences"
        const val DARK_THEME = "dark_theme"
    }
}