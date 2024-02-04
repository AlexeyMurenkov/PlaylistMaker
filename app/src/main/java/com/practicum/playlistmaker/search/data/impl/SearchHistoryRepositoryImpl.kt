package com.practicum.playlistmaker.search.data.impl

import com.practicum.playlistmaker.search.data.SearchHistoryRepository
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.domain.models.TracksList
import com.practicum.playlistmaker.utils.data.Repository

class SearchHistoryRepositoryImpl(val capacity: Int, val repository: Repository<TracksList>) :
    SearchHistoryRepository {

    val mutableHistory = mutableListOf<Track>()

    override var onChangeHistory: ((List<Track>) -> Unit)? = null

    init {
        with (repository.storage) {
            if (list != null) {
                mutableHistory.addAll(list)
            }
        }
    }
    override val history: List<Track>
        get() = mutableHistory.toList()

    override fun add(track: Track) {
        with(mutableHistory) {
            clear()
            add(track)
            repository.storage.list
                ?.filterNot { it == track }
                ?.forEach { add(it) }
            if (size > capacity) removeAt(capacity)
        }
        save()
    }

    override fun clear() {
        mutableHistory.clear()
        save()
    }

    private fun save() {
        repository.storage = TracksList(history)
        onChangeHistory?.invoke(history)
    }
}