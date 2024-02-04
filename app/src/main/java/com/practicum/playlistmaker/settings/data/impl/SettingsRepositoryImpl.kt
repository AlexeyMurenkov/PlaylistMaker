package com.practicum.playlistmaker.settings.data.impl

import com.practicum.playlistmaker.settings.data.SettingsRepository
import com.practicum.playlistmaker.settings.data.models.ApplicationSettings
import com.practicum.playlistmaker.utils.data.Repository

class SettingsRepositoryImpl(
    val repository: Repository<ApplicationSettings>
) : SettingsRepository {
    override var aplicationSettings: ApplicationSettings
        get() = repository.storage
        set(value) {
            repository.storage = value
        }
}
