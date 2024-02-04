package com.practicum.playlistmaker.search.domain.models

sealed class SearchScreenState {
    class FoundTracks(val tracksList: List<Track>) : SearchScreenState()
    class History(val tracksList: List<Track>) : SearchScreenState()
    class Process : SearchScreenState()
    class Error: SearchScreenState()
}