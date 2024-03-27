package com.practicum.playlistmaker.media.domain.impl

import com.practicum.playlistmaker.media.domain.FavoritesInteractor
import com.practicum.playlistmaker.media.domain.FavoritesRepository
import com.practicum.playlistmaker.search.domain.TrackRepository
import com.practicum.playlistmaker.search.domain.models.Track

class FavoritesInteractorImpl(
    val favoritesRepository: FavoritesRepository,
    val trackRepository: TrackRepository
) : FavoritesInteractor {
    override suspend fun add(track: Track) {
        favoritesRepository.add(track)
    }

    override suspend fun remove(track: Track) {
        favoritesRepository.remove(track)
    }

    override suspend fun getFavorites() = favoritesRepository.getFavorites()

    override suspend fun isFavorite(track: Track) = favoritesRepository.isFavorite(track.trackId)

    override fun play(track: Track) {
        trackRepository.play(track)
    }
}