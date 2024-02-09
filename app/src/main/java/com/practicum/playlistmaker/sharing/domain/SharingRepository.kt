package com.practicum.playlistmaker.sharing.domain

import com.practicum.playlistmaker.sharing.domain.models.EmailData

interface SharingRepository {
    val shareAppLink: String
    val agreementLink: String
    val supportMail: EmailData
}
