package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val back = findViewById<TextView>(R.id.settings_back)
        back.setOnClickListener {
            finish()
        }

        val shareApp = findViewById<TextView>(R.id.settings_share_app)
        shareApp.setOnClickListener {
            shareApp()
        }

        val support = findViewById<TextView>(R.id.settings_support)
        support.setOnClickListener {
            mailToSupport()
        }

        val agreement = findViewById<TextView>(R.id.settings_agreement)
        agreement.setOnClickListener {
            showAgreement()
        }
    }

    fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text))
            .type = "text/plain"
        startActivity(intent)
    }

    fun mailToSupport() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent
            .setData(Uri.parse("mailto:"))
            .putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.support_mail)))
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_default_subject))
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.support_default_text))
        startActivity(intent)
    }

    fun showAgreement() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent
            .setData(Uri.parse(getString(R.string.agreement)))
        startActivity(intent)
    }
}