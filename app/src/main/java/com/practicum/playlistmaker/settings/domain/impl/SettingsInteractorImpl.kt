package com.practicum.playlistmaker.settings.domain.impl

import com.practicum.playlistmaker.settings.data.SettingsRepository
import com.practicum.playlistmaker.settings.data.models.ApplicationSettings
import com.practicum.playlistmaker.settings.domain.SettingsInteractor

class SettingsInteractorImpl(val settingsRepository: SettingsRepository) : SettingsInteractor {
    override var isDarkMode: Boolean
        get() = settingsRepository.aplicationSettings.isDarkMode
        set(value) {
            settingsRepository.aplicationSettings = ApplicationSettings(value)
        }
}
