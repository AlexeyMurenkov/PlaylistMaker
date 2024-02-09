package com.practicum.playlistmaker.main.di

import com.practicum.playlistmaker.main.domain.MainInteractor
import com.practicum.playlistmaker.main.domain.impl.MainInteractorImpl
import org.koin.dsl.module

val mainDomainModule = module {
    single<MainInteractor> {
        MainInteractorImpl(mainRepository = get())
    }
}