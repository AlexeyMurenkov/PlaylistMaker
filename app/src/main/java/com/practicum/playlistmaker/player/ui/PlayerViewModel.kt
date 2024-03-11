package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.player.domain.PlayerInteractor
import com.practicum.playlistmaker.player.domain.models.PlayerScreenState
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(private val playerInteractor: PlayerInteractor) : ViewModel() {

    private val playerScreenState = MutableLiveData<PlayerScreenState>()
    fun getPlayerScreenState(): LiveData<PlayerScreenState> = playerScreenState

    private var timerJob: Job? = null

    private fun startTimer() {
        with(playerInteractor) {
            timerJob = viewModelScope.launch {
                while (state != PlayerState.PAUSED) {
                    delay(REFRESH_POSITION_PERIOD)
                    playerScreenState.value = PlayerScreenState(state, currentPosition)
                }
            }
        }
    }

    fun preparePlayer(track: Track) {
        playerInteractor.prepare(track)
        startTimer()
    }

    fun pause() {
        playerInteractor.pause()
    }

    fun play() {
        playerInteractor.play()
        startTimer()
    }

    fun playPause() {
        when (playerInteractor.state) {
            PlayerState.PLAYING -> pause()
            PlayerState.PREPARED, PlayerState.PAUSED -> play()
            else -> {}
        }
    }

    companion object {
        private const val REFRESH_POSITION_PERIOD = 300L
    }
}
