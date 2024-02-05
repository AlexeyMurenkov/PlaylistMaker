package com.practicum.playlistmaker.player.data.impl

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.practicum.playlistmaker.player.data.PlayerRepository
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track

class PlayerRepositoryImpl : PlayerRepository {

    private val player = MediaPlayer()
    private var playerState = PlayerState.DEFAULT

    private val handler = Handler(Looper.getMainLooper())
    private val positionUpdater = Runnable { updatePosition() }

    override var onChangeStateListener : ((PlayerState) -> Unit)? = null
    override var onPositionChangeListener: ((Int) -> Unit)? = null

    override val state: PlayerState
        get() = playerState

    override val duration: Int
        get() = player.duration

    override var currentPosition: Int
        get() = if (state == PlayerState.DEFAULT) 0 else player.currentPosition
        set(msec) {
            player.seekTo(msec)
            onPositionChangeListener?.invoke(msec)
        }

    override fun prepare(track: Track) {
        if (track.previewUrl.isNullOrBlank()) return
        onPositionChangeListener?.invoke(0)
        with(player) {
            setDataSource(track.previewUrl)
            setOnPreparedListener { setState(PlayerState.PREPARED) }
            prepareAsync()
        }
    }

    override fun play() {
        if (state == PlayerState.PREPARED || state == PlayerState.PAUSED) {
            player.start()
            setState(PlayerState.PLAYING)
            updatePosition()
        }
    }

    override fun pause() {
        if (state == PlayerState.PLAYING) {
            player.pause()
            setState(PlayerState.PAUSED)
            handler.removeCallbacks(positionUpdater)
        }
    }

    private fun updatePosition() {
        onPositionChangeListener?.invoke(currentPosition)
        if (!player.isPlaying) {
            setState(PlayerState.PAUSED)
            handler.removeCallbacks(positionUpdater)
        } else {
            handler.postDelayed(positionUpdater, REFRESH_POSITION_PERIOD)
        }
    }

    private fun setState(playerState: PlayerState) {
        this.playerState = playerState
        onChangeStateListener?.invoke(playerState)
    }

    companion object {
        private const val REFRESH_POSITION_PERIOD = 1000L
    }
}
