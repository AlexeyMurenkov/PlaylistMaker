package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.player.domain.PlayerInteractor
import com.practicum.playlistmaker.player.domain.models.PlayerScreenState
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.utils.Creator

class PlayerViewModel(private val track: Track, private val playerInteractor: PlayerInteractor) :
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

    fun preparePlayer() {
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

    companion object {
        fun getViewModelFactory(track: Track): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val playerInteractor = Creator.providePlayerInteractor()

                PlayerViewModel(track, playerInteractor)
            }
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
