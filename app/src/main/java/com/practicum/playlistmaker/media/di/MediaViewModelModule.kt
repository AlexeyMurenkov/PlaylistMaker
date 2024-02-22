package com.practicum.playlistmaker.media.di

import com.practicum.playlistmaker.media.domain.PlaylistsInteractor
import com.practicum.playlistmaker.media.ui.fragments.FavoritesViewModel
import com.practicum.playlistmaker.media.ui.fragments.PlaylistsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mediaViewModelModule = module {
    viewModel {
        PlaylistsViewModel(object : PlaylistsInteractor {
            override fun newPlayList() {
                // Заглушка
            }
        })
    }
    viewModel {
        FavoritesViewModel()
    }
}