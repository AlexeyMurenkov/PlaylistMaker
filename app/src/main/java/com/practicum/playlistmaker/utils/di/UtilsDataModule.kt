package com.practicum.playlistmaker.utils.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilsDataModule = module {
    single {
        androidContext()
            .getSharedPreferences(NAME_SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
    }
}

private const val NAME_SETTINGS_PREFERENCES = "settings_preferences"
