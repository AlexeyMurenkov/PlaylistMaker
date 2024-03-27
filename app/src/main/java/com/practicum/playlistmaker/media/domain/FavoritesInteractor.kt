package com.practicum.playlistmaker.media.domain

import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface FavoritesInteractor {
    suspend fun add(track: Track)
    suspend fun remove(track: Track)
    suspend fun getFavorites(): Flow<List<Track>>
    suspend fun isFavorite(track: Track): Flow<Boolean>
    fun play(track: Track)
}