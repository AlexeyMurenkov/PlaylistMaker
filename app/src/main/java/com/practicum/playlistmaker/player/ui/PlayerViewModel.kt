package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmaker.player.domain.PlayerInteractor
import com.practicum.playlistmaker.player.domain.models.PlayerScreenState
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track

class PlayerViewModel(private val playerInteractor: PlayerInteractor) :
    ViewModel() {

    private val playerScreenState = MutableLiveData<PlayerScreenState>()
    fun getPlayerScreenState(): LiveData<PlayerScreenState> = playerScreenState

    init {
        with(playerInteractor) {
            onChangeStateListener = {
                playerScreenState.value = PlayerScreenState.State(it)
                if (it == PlayerState.PAUSED && currentPosition >= duration) {
                    currentPosition = 0
                }
            }
            onChangePositionListener = { playerScreenState.value = PlayerScreenState.Position(it) }
        }
    }

    fun preparePlayer(track: Track) {
        playerInteractor.prepare(track)
    }

    fun pause() {
        playerInteractor.pause()
    }

    fun play() {
        playerInteractor.play()
    }

    fun playPause() {
        when (playerInteractor.state) {
            PlayerState.PLAYING -> pause()
            PlayerState.PREPARED, PlayerState.PAUSED -> play()
            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        with(playerInteractor) {
            clear()
            onChangeStateListener = null
            onChangePositionListener = null
        }
    }
}
