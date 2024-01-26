package com.practicum.playlistmaker.domain.api

import com.practicum.playlistmaker.domain.models.Storage
import com.practicum.playlistmaker.domain.models.Track

interface TrackRepository {
    fun searchTracks(expression: String) : Storage<List<Track>>
}