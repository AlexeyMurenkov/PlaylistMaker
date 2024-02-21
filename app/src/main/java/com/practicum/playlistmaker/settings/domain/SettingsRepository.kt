package com.practicum.playlistmaker.settings.domain

import com.practicum.playlistmaker.settings.data.models.ApplicationSettings

interface SettingsRepository {
    var aplicationSettings: ApplicationSettings
}
