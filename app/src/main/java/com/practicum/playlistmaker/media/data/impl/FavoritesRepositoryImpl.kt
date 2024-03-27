package com.practicum.playlistmaker.media.data.impl

import com.practicum.playlistmaker.media.data.converters.TrackDbConverter
import com.practicum.playlistmaker.media.data.db.PlaylistMakerDatabase
import com.practicum.playlistmaker.media.domain.FavoritesRepository
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoritesRepositoryImpl(
    private val database: PlaylistMakerDatabase,
    private val converter: TrackDbConverter
) : FavoritesRepository {

    override suspend fun add(track: Track) {
        val trackEntity = converter.map(track)
        database.trackDao().add(trackEntity)
    }

    override suspend fun remove(track: Track) {
        val trackEntity = converter.map(track)
        database.trackDao().remove(trackEntity)
    }

    override suspend fun getFavorites(): Flow<List<Track>> = flow {
        val favorites = database.trackDao().getFavorites()
        emit(favorites.map { trackEntity -> converter.map(trackEntity) })
    }

    override suspend fun isFavorite(trackId: Long): Flow<Boolean> = flow {
        emit(database.trackDao().isFavorite(trackId) > 0)
    }
}
