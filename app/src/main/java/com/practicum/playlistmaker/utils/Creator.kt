package com.practicum.playlistmaker.utils

import android.content.Context
import com.practicum.playlistmaker.main.data.impl.MainRepositoryImpl
import com.practicum.playlistmaker.search.data.impl.TrackRepositoryImpl
import com.practicum.playlistmaker.search.data.network.impl.ITunesNetworkClient
import com.practicum.playlistmaker.main.domain.impl.MainInteractorImpl
import com.practicum.playlistmaker.player.data.impl.PlayerRepositoryImpl
import com.practicum.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmaker.search.data.impl.SearchHistoryRepositoryImpl
import com.practicum.playlistmaker.search.domain.impl.SearchInteractorImpl
import com.practicum.playlistmaker.search.domain.models.TracksList
import com.practicum.playlistmaker.settings.data.impl.SettingsRepositoryImpl
import com.practicum.playlistmaker.settings.data.models.ApplicationSettings
import com.practicum.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.practicum.playlistmaker.sharing.data.impl.ExternalNavigatorImpl
import com.practicum.playlistmaker.sharing.data.impl.SharingRepositoryImpl
import com.practicum.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import com.practicum.playlistmaker.utils.data.SharedPreferencesRepository

object Creator {
    fun provideSettingsInteractor(context: Context) =
        SettingsInteractorImpl(
            SettingsRepositoryImpl(
                SharedPreferencesRepository(
                    context,
                    ApplicationSettings::class.java,
                    NAME_SETTINGS_PREFERENCES,
                    KEY_APPLICATION_SETTINGS
                )
            )
        )

    fun provideSharingInteractor(context: Context) =
        SharingInteractorImpl(ExternalNavigatorImpl(context), SharingRepositoryImpl(context))

    fun provideMainInteractor(context: Context) = MainInteractorImpl(MainRepositoryImpl(context))

    fun providePlayerInteractor() = PlayerInteractorImpl(PlayerRepositoryImpl())

    fun provideSearchInteractor(context: Context) =
        SearchInteractorImpl(
            TrackRepositoryImpl(context, ITunesNetworkClient()), SearchHistoryRepositoryImpl(
                SEARCH_HISTORY_CAPACITY,
                SharedPreferencesRepository(
                    context.applicationContext,
                    TracksList::class.java,
                    NAME_SETTINGS_PREFERENCES,
                    KEY_SEARCH_HISTORY
                )
            )
        )

    private const val NAME_SETTINGS_PREFERENCES = "settings_preferences"

    private const val KEY_APPLICATION_SETTINGS = "application_settings"
    private const val KEY_SEARCH_HISTORY = "history"

    private const val SEARCH_HISTORY_CAPACITY = 10
}