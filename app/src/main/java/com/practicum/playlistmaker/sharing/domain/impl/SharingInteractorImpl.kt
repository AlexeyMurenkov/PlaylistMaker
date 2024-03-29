package com.practicum.playlistmaker.sharing.domain.impl

import com.practicum.playlistmaker.sharing.data.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.SharingRepository
import com.practicum.playlistmaker.sharing.domain.SharingInteractor

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
    private val sharingRepository: SharingRepository
) : SharingInteractor {

    override fun shareApp() {
        externalNavigator.shareLink(sharingRepository.shareAppLink)
    }

    override fun openTerms() {
        externalNavigator.openLink(sharingRepository.agreementLink)
    }

    override fun openSupport() {
        externalNavigator.sendEmail(sharingRepository.supportMail)
    }
}
