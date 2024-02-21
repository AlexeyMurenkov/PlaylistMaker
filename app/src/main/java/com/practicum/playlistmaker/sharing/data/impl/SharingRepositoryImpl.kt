package com.practicum.playlistmaker.sharing.data.impl

import android.content.Context
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.sharing.domain.SharingRepository
import com.practicum.playlistmaker.sharing.domain.models.EmailData

class SharingRepositoryImpl(private val context: Context) : SharingRepository {
    override val shareAppLink: String
        get() = context.getString(R.string.share_app_link)
    override val agreementLink: String
        get() = context.getString(R.string.agreement_link)
    override val supportMail: EmailData
        get() = EmailData(
            context.getString(R.string.support_mail),
            context.getString(R.string.support_default_subject),
            context.getString(R.string.support_default_text)
        )
}
