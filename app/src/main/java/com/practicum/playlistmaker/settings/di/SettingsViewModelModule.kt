package com.practicum.playlistmaker.settings.di

import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.settings.ui.SettingsViewModel
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigatorImpl
import com.practicum.playlistmaker.sharing.data.impl.SharingRepositoryImpl
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsViewModelModule = module {
    viewModel {
        SettingsViewModel(
            SettingsInteractorImpl(settingsRepository = get()),
            SharingInteractorImpl(
                ExternalNavigatorImpl(androidApplication()),
                SharingRepositoryImpl(androidApplication())
            )
        )
    }
}
