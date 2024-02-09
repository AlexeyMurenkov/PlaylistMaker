package com.practicum.playlistmaker.main.di

import com.practicum.playlistmaker.main.domain.impl.MainInteractorImpl
import com.practicum.playlistmaker.main.ui.MainViewModel
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {
    viewModel {
        MainViewModel(
            MainInteractorImpl(mainRepository = get()),
            SettingsInteractorImpl(settingsRepository = get())
        )
    }
}