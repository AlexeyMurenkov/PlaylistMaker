package com.practicum.playlistmaker.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.practicum.playlistmaker.sharing.data.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.models.EmailData

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {
    override fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent
            .putExtra(Intent.EXTRA_TEXT, link)
            .type = "text/plain"
        context.startActivity(intent)
    }

    override fun openLink(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent
            .setData(Uri.parse(link))
        context.startActivity(intent)
    }

    override fun sendEmail(emailData: EmailData) {
        val intent = Intent(Intent.ACTION_SENDTO)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        with(emailData) {
            intent
                .setData(Uri.parse("mailto:"))
                .putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, text)
        }
        context.startActivity(intent)
    }
}