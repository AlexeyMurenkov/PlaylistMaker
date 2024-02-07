package com.practicum.playlistmaker.main.di

import com.practicum.playlistmaker.main.data.MainRepository
import com.practicum.playlistmaker.main.data.impl.MainRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val mainDataModule = module {
    single<MainRepository> {
        MainRepositoryImpl(androidApplication())
    }
}