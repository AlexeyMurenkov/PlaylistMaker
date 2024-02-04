package com.practicum.playlistmaker.search.data

import com.practicum.playlistmaker.search.domain.models.Resource
import com.practicum.playlistmaker.search.domain.models.Track

interface TrackRepository {
    fun search(expression: String) : Resource<List<Track>>

    fun play(track: Track)
}
