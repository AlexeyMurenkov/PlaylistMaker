package com.practicum.playlistmaker.main.domain.impl

import com.practicum.playlistmaker.main.domain.MainRepository
import com.practicum.playlistmaker.main.domain.MainInteractor

class MainInteractorImpl(private val mainRepository: MainRepository) : MainInteractor {
    override fun openSearchActivity() {
        mainRepository.openSearchActivity()
    }

    override fun openMediaActivity() {
        mainRepository.openMediaActivity()
    }

    override fun openSettingsActivity() {
        mainRepository.openSettingsActivity()
    }
}