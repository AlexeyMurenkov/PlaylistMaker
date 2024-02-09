package com.practicum.playlistmaker.settings.di

import com.practicum.playlistmaker.settings.domain.SettingsRepository
import com.practicum.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.data.models.ApplicationSettings
import com.practicum.playlistmaker.utils.data.SharedPreferencesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val settingsDataModule = module {
    single(named("ApplicationSettings")) {
        SharedPreferencesRepository(
            ApplicationSettings::class.java,
            get(),
            KEY_APPLICATION_SETTINGS
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(named("ApplicationSettings")))
    }
}

private const val KEY_APPLICATION_SETTINGS = "application_settings"