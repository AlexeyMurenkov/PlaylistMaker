package com.practicum.playlistmaker.player.di

import android.media.MediaPlayer
import com.practicum.playlistmaker.player.domain.PlayerRepository
import com.practicum.playlistmaker.player.data.impl.PlayerRepositoryImpl
import org.koin.dsl.module

val playerDataModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl(player = get())
    }

    single {
        MediaPlayer()
    }
}