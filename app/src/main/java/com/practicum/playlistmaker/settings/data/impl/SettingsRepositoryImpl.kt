package com.practicum.playlistmaker.settings.data.impl

import com.practicum.playlistmaker.settings.data.SettingsRepository
import com.practicum.playlistmaker.settings.data.models.ApplicationSettings
import com.practicum.playlistmaker.utils.data.SharedPreferencesRepository

class SettingsRepositoryImpl(
    private val repository: SharedPreferencesRepository<ApplicationSettings>
) : SettingsRepository {
    override var aplicationSettings: ApplicationSettings
        get() = repository.storage
        set(value) {
            repository.storage = value
        }
}
