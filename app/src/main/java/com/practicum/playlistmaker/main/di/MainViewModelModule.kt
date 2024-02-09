package com.practicum.playlistmaker.main.di

import com.practicum.playlistmaker.main.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainViewModelModule = module {
    viewModel {
        MainViewModel(mainInteractor = get(), settingsInteractor = get())
    }
}