package com.practicum.playlistmaker.sharing.di

import com.practicum.playlistmaker.sharing.data.ExternalNavigator
import com.practicum.playlistmaker.sharing.domain.SharingRepository
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigatorImpl
import com.practicum.playlistmaker.sharing.data.impl.SharingRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharingDataModule = module {
    single<SharingRepository> {
        SharingRepositoryImpl(androidContext())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(androidContext())
    }
}
