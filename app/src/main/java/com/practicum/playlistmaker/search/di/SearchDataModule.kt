package com.practicum.playlistmaker.search.di

import com.practicum.playlistmaker.search.domain.SearchHistoryRepository
import com.practicum.playlistmaker.search.domain.TrackRepository
import com.practicum.playlistmaker.search.data.impl.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.search.data.impl.TrackRepositoryImpl
import com.practicum.playlistmaker.search.data.network.ITunesSearchApi
import com.practicum.playlistmaker.search.data.network.NetworkClient
import com.practicum.playlistmaker.search.data.network.impl.ITunesNetworkClient

import com.practicum.playlistmaker.search.domain.models.TracksList
import com.practicum.playlistmaker.utils.data.SharedPreferencesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val searchDataModule = module {
    single(named("TrackList")) {
        SharedPreferencesRepository(TracksList::class.java, get(), KEY_SEARCH_HISTORY)
    }

    single<ITunesSearchApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesSearchApi::class.java)
    }

    single<NetworkClient> {
        ITunesNetworkClient(get())
    }

    single<TrackRepository> {
        TrackRepositoryImpl(get(), get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(SEARCH_HISTORY_CAPACITY, get(named("TrackList")))
    }
}

private const val KEY_SEARCH_HISTORY = "history"
private const val SEARCH_HISTORY_CAPACITY = 10

private const val BASE_URL = "https://itunes.apple.com"