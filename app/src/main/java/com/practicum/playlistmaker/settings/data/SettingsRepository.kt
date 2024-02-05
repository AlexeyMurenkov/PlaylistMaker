package com.practicum.playlistmaker.settings.data

import com.practicum.playlistmaker.settings.data.models.ApplicationSettings

interface SettingsRepository {
    var aplicationSettings: ApplicationSettings
}
