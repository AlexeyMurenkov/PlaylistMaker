package com.practicum.playlistmaker.player.di

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper

import com.practicum.playlistmaker.player.data.PlayerRepository
import com.practicum.playlistmaker.player.data.impl.PlayerRepositoryImpl
import org.koin.dsl.module


val playerDataModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl(get(), get())
    }

    single {
        MediaPlayer()
    }

    single {
        Handler(Looper.getMainLooper())
    }
}