package com.practicum.playlistmaker.player.domain

import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track

interface PlayerInteractor {

    val state: PlayerState
    val duration: Int
    var currentPosition: Int

    var onChangeStateListener: ((PlayerState) -> Unit)?
    var onChangePositionListener: ((Int) -> Unit)?

    fun prepare(track: Track)
    fun play()
    fun pause()
}
