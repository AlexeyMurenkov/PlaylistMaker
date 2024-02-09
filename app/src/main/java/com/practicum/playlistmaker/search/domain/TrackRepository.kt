package com.practicum.playlistmaker.search.domain

import com.practicum.playlistmaker.search.domain.models.Track

interface TrackRepository {
    fun search(expression: String) : Result<List<Track>>

    fun play(track: Track)
}
