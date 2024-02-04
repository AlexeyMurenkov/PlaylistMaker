package com.practicum.playlistmaker.search.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmaker.search.domain.SearchInteractor
import com.practicum.playlistmaker.search.domain.models.Resource
import com.practicum.playlistmaker.search.domain.models.SearchScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.utils.Creator

class SearchViewModel(application: Application, val searchInteractor: SearchInteractor) : AndroidViewModel(application) {

    private val searchScreenState = MutableLiveData<SearchScreenState>()
    fun getPlayerScreenState(): LiveData<SearchScreenState> = searchScreenState

    init {
        searchScreenState.postValue(SearchScreenState.History(searchInteractor.history))
        searchInteractor.onChangeHistory = {
            searchScreenState.postValue(SearchScreenState.History(it))
        }
    }
    fun search(expression: String) {
        if (expression.isBlank()) return
        searchScreenState.value = SearchScreenState.Process()
        searchInteractor.search(expression) {
            when (it) {
                is Resource.Error -> searchScreenState.value = SearchScreenState.Error()
                is Resource.Success -> searchScreenState.postValue(SearchScreenState.FoundTracks(it.data))
            }
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
                val application =
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application

                val searchInteractor = Creator.provideSearchInteractor(context)
                SearchViewModel(application, searchInteractor)
            }
        }
    }
}