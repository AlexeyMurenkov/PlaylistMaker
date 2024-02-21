package com.practicum.playlistmaker.search.domain.impl

import com.practicum.playlistmaker.search.domain.SearchHistoryRepository
import com.practicum.playlistmaker.search.domain.TrackRepository
import com.practicum.playlistmaker.search.domain.SearchInteractor
import com.practicum.playlistmaker.search.domain.models.Track
import java.util.concurrent.Executors

class SearchInteractorImpl (
    private val trackRepository: TrackRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : SearchInteractor {

    private val executor = Executors.newCachedThreadPool()

    override var onChangeHistory: ((List<Track>) -> Unit)? = null
    override val history: List<Track>
        get() = searchHistoryRepository.history

    init {
        searchHistoryRepository.onChangeHistory = { onChangeHistory?.invoke(it) }
    }

    override fun search(expression: String, consumer: SearchInteractor.Consumer) {
        executor.execute { consumer.consume(trackRepository.search(expression)) }
    }

    override fun play(track: Track) {
        trackRepository.play(track)
        searchHistoryRepository.add(track)
    }

    override fun addToHistory(track: Track) {
        searchHistoryRepository.add(track)
    }

    override fun clearHistory() {
        searchHistoryRepository.clear()
    }

    override fun clear() {
        searchHistoryRepository.onChangeHistory = null
    }
}
