package com.practicum.playlistmaker.settings.domain.impl

import com.practicum.playlistmaker.settings.domain.SettingsRepository
import com.practicum.playlistmaker.settings.data.models.ApplicationSettings
import com.practicum.playlistmaker.settings.domain.SettingsInteractor

class SettingsInteractorImpl(private val settingsRepository: SettingsRepository) :
    SettingsInteractor {
    override var isDarkMode: Boolean
        get() = settingsRepository.aplicationSettings.isDarkMode
        set(value) {
            settingsRepository.aplicationSettings = ApplicationSettings(value)
        }
}
