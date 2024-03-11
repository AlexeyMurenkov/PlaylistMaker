package com.practicum.playlistmaker.player.domain.impl

import com.practicum.playlistmaker.player.domain.PlayerRepository
import com.practicum.playlistmaker.player.domain.PlayerInteractor
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track

class PlayerInteractorImpl(private val playerRepository: PlayerRepository) : PlayerInteractor {
    override val state: PlayerState
        get() = playerRepository.state
    override var currentPosition: Int
        get() = playerRepository.currentPosition
        set(value) {
            playerRepository.currentPosition = value
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
