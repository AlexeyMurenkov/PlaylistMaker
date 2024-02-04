package com.practicum.playlistmaker.search.domain

import com.practicum.playlistmaker.search.domain.models.Resource
import com.practicum.playlistmaker.search.domain.models.Track

interface SearchInteractor {

    var onChangeHistory: ((List<Track>) -> Unit)?

    val history: List<Track>
    fun search(expression: String, consumer: Consumer)
    fun play(track: Track)
    fun addToHistory(track: Track)
    fun clearHistory()

    fun interface Consumer {
        fun consume(foundTracks: Resource<List<Track>>)
    }
}