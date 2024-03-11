package com.practicum.playlistmaker.player.data.impl

import android.media.MediaPlayer
import com.practicum.playlistmaker.player.domain.PlayerRepository
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track

class PlayerRepositoryImpl(private val player: MediaPlayer) :
    PlayerRepository {

    private var playerState = PlayerState.DEFAULT

    override val state: PlayerState
        get() = playerState

    override var currentPosition: Int
        get() = if (state == PlayerState.DEFAULT) 0 else player.currentPosition
        set(msec) {
            player.seekTo(msec)
        }

    override fun prepare(track: Track) {
        if (track.previewUrl.isNullOrBlank()) return
        with(player) {
            reset()
            playerState = PlayerState.DEFAULT
            setDataSource(track.previewUrl)
            setOnPreparedListener { playerState = PlayerState.PREPARED }
            setOnCompletionListener { playerState = PlayerState.PREPARED }
            prepareAsync()
        }
    }

    override fun play() {
        if (state == PlayerState.PREPARED || state == PlayerState.PAUSED) {
            player.start()
            playerState = PlayerState.PLAYING
        }
    }

    override fun pause() {
        if (state == PlayerState.PLAYING) {
            player.pause()
            playerState = PlayerState.PAUSED
        }
    }
}
