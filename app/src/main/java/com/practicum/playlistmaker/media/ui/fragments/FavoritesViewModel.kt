package com.practicum.playlistmaker.media.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.media.domain.FavoritesInteractor
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {
    private val _favoritesTrackList = MutableLiveData<List<Track>>()
    val favoritesTrackList: LiveData<List<Track>> = _favoritesTrackList

    fun fillFavorites() {
        viewModelScope.launch (Dispatchers.IO) {
            favoritesInteractor
                .getFavorites()
                .collect { _favoritesTrackList.postValue(it)}
        }
    }

    fun play(track: Track) {
        favoritesInteractor.play(track)
    }
}

