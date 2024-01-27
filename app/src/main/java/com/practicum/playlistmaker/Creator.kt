package com.practicum.playlistmaker

import com.practicum.playlistmaker.data.TrackRepositoryImpl
import com.practicum.playlistmaker.data.network.ITunesNetworkClient
import com.practicum.playlistmaker.domain.api.TracksInteractor
import com.practicum.playlistmaker.domain.impl.TracksInteractorImpl

object Creator {
    fun provideTracksInteractor() : TracksInteractor {
        return TracksInteractorImpl(TrackRepositoryImpl(ITunesNetworkClient()))
    }
}