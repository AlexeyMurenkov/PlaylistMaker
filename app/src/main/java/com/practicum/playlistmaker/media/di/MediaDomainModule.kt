package com.practicum.playlistmaker.media.di

import com.practicum.playlistmaker.media.domain.FavoritesInteractor
import com.practicum.playlistmaker.media.domain.impl.FavoritesInteractorImpl
import org.koin.dsl.module

val mediaDomainModule = module {
    single<FavoritesInteractor> { FavoritesInteractorImpl(favoritesRepository = get(), trackRepository = get()) }
}