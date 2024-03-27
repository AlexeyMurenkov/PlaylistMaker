package com.practicum.playlistmaker.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.search.domain.SearchInteractor
import com.practicum.playlistmaker.search.domain.models.SearchScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {

    private val _playerScreenState = MutableLiveData<SearchScreenState>()
    val playerScreenState: LiveData<SearchScreenState> = _playerScreenState

    init {
        _playerScreenState.postValue(SearchScreenState.History(searchInteractor.history))
        searchInteractor.onChangeHistory = {
            _playerScreenState.postValue(SearchScreenState.History(it))
        }
    }

    override fun onCleared() {
        super.onCleared()
        with(searchInteractor) {
            clear()
            onChangeHistory = null
        }
    }

    fun search(expression: String) {
        if (expression.isBlank()) return
        _playerScreenState.value = SearchScreenState.Process()
        viewModelScope.launch {
            searchInteractor
                .search(expression).collect {
                    it.onFailure { _playerScreenState.postValue(SearchScreenState.Error()) }
                    it.onSuccess { _playerScreenState.postValue(SearchScreenState.FoundTracks(it)) }
                }
        }
    }

    fun play(track: Track) {
        searchInteractor.play(track)
    }

    fun clearHistory() {
        searchInteractor.clearHistory()
    }
}
