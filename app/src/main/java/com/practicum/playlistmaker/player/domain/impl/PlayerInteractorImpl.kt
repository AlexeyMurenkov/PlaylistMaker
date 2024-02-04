package com.practicum.playlistmaker.player.domain.impl

import com.practicum.playlistmaker.player.data.PlayerRepository
import com.practicum.playlistmaker.player.domain.PlayerInteractor
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track

class PlayerInteractorImpl(val playerRepository: PlayerRepository) : PlayerInteractor {
    override val state: PlayerState
        get() = playerRepository.state
    override val duration: Int
        get() = playerRepository.duration
    override var currentPosition: Int
        get() = playerRepository.currentPosition
        set(value) {
            playerRepository.currentPosition = value
        }

    override var onChangeStateListener: ((PlayerState) -> Unit)? = null
    override var onChangePositionListener: ((Int) -> Unit)? = null

    init {
        playerRepository.onChangeStateListener = { onChangeStateListener?.invoke(it) }
        playerRepository.onPositionChangeListener = { onChangePositionListener?.invoke(it) }
    }

    override fun prepare(track: Track) {
        playerRepository.prepare(track)
    }

    override fun play() {
        playerRepository.play()
    }

    override fun pause() {
        playerRepository.pause()
    }
}
