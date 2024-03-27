package com.practicum.playlistmaker.media.di

import androidx.room.Room
import com.practicum.playlistmaker.media.data.converters.TrackDbConverter
import com.practicum.playlistmaker.media.data.db.PlaylistMakerDatabase
import com.practicum.playlistmaker.media.domain.FavoritesRepository
import com.practicum.playlistmaker.media.data.impl.FavoritesRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mediaDataModule = module {
    single {
        Room
            .databaseBuilder(androidContext(), PlaylistMakerDatabase::class.java, "playlistmaker.db")
            .build()
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(database = get(), TrackDbConverter())
    }
}
