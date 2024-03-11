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

    private val searchScreenState = MutableLiveData<SearchScreenState>()
    fun getPlayerScreenState(): LiveData<SearchScreenState> = searchScreenState

    init {
        searchScreenState.postValue(SearchScreenState.History(searchInteractor.history))
        searchInteractor.onChangeHistory = {
            searchScreenState.postValue(SearchScreenState.History(it))
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
        searchScreenState.value = SearchScreenState.Process()
        viewModelScope.launch {
            searchInteractor
                .search(expression).collect {
                    it.onFailure { searchScreenState.postValue(SearchScreenState.Error()) }
                    it.onSuccess { searchScreenState.postValue(SearchScreenState.FoundTracks(it)) }
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
