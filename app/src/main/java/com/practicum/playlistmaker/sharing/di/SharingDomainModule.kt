package com.practicum.playlistmaker.sharing.di

import com.practicum.playlistmaker.sharing.domain.SharingInteractor
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val sharingDomainModule = module {
    single<SharingInteractor> {
        SharingInteractorImpl(externalNavigator = get(), sharingRepository = get())
    }
}