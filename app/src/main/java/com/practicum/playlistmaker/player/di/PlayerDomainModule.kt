package com.practicum.playlistmaker.player.di

import com.practicum.playlistmaker.player.domain.PlayerInteractor
import com.practicum.playlistmaker.player.domain.impl.PlayerInteractorImpl
import org.koin.dsl.module

val playerDomainModule = module {
    single<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }
}