package com.practicum.playlistmaker.search.domain

import com.practicum.playlistmaker.search.domain.models.Track

interface SearchHistoryRepository {

    val history: List<Track>

    var onChangeHistory: ((List<Track>) -> Unit)?

    fun add(track: Track)
    fun clear()
}
