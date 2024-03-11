package com.practicum.playlistmaker.player.domain

import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track

interface PlayerRepository {

    val state: PlayerState
    var currentPosition: Int

    fun prepare(track: Track)
    fun play()
    fun pause()
}