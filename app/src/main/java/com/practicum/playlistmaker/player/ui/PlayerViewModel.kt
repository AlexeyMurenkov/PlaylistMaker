package com.practicum.playlistmaker.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.media.domain.FavoritesInteractor
import com.practicum.playlistmaker.player.domain.PlayerInteractor
import com.practicum.playlistmaker.player.domain.models.PlayerScreenState
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private val _playerScreenState = MutableLiveData<PlayerScreenState>()
    val playerScreenState: LiveData<PlayerScreenState> = _playerScreenState

    private var timerJob: Job? = null

    private fun startTimer() {
        with(playerInteractor) {
            timerJob = viewModelScope.launch {
                while (state != PlayerState.PAUSED) {
                    delay(REFRESH_POSITION_PERIOD)
                    _playerScreenState.value = PlayerScreenState.Progress(state, currentPosition)
                }
            }
        }
    }

    fun preparePlayer(track: Track) {
        playerInteractor.prepare(track)
        startTimer()
        viewModelScope.launch (Dispatchers.IO) {
            _playerScreenState.postValue(
                PlayerScreenState.Favorite(favoritesInteractor.isFavorite(track).single())
            )
        }
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

    fun changeFavorite(track: Track) {
        viewModelScope.launch (Dispatchers.IO) {
            with(favoritesInteractor) {
                val isFavorite = isFavorite(track).single()
                if (isFavorite) {
                    remove(track)
                } else {
                    add(track)
                }
                _playerScreenState.postValue(PlayerScreenState.Favorite(!isFavorite))
            }
        }
    }

    companion object {
        private const val REFRESH_POSITION_PERIOD = 300L
    }
}
