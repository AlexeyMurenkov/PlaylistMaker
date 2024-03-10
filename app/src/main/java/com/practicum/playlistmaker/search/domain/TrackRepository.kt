package com.practicum.playlistmaker.search.domain

import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun search(expression: String) : Flow<Result<List<Track>>>

    fun play(track: Track)
}
