package com.practicum.playlistmaker.search.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.search.domain.SearchInteractor
import com.practicum.playlistmaker.search.domain.models.SearchScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.utils.Creator

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
        searchInteractor.search(expression) {
            it.onFailure { searchScreenState.postValue(SearchScreenState.Error()) }
            it.onSuccess { searchScreenState.postValue(SearchScreenState.FoundTracks(it)) }
        }
    }

    fun play(track: Track) {
        searchInteractor.play(track)
    }

    fun clearHistory() {
        searchInteractor.clearHistory()
    }

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val searchInteractor = Creator.provideSearchInteractor(context)
                SearchViewModel(searchInteractor)
            }
        }
    }
}
