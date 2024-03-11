package com.practicum.playlistmaker.search.domain

import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {

    var onChangeHistory: ((List<Track>) -> Unit)?

    val history: List<Track>
    fun search(expression: String): Flow<Result<List<Track>>>
    fun play(track: Track)
    fun addToHistory(track: Track)
    fun clearHistory()

    fun clear()
}
