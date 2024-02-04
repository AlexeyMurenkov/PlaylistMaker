package com.practicum.playlistmaker.sharing.data

import com.practicum.playlistmaker.sharing.domain.models.EmailData

interface ExternalNavigator {
    fun shareLink(link: String)
    fun openLink(link: String)
    fun sendEmail(emailData: EmailData)
}
