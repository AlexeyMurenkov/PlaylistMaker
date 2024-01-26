package com.practicum.playlistmaker.presentation.track.history

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.domain.models.Track

class SearchHistory(
    val sharedPreferences: SharedPreferences
) {

    val history = mutableListOf<Track>()

    init {
        history.addAll(read())
    }

    fun add(track: Track) {
        with(history) {
            clear()
            add(track)
            read()
                .filterNot { it == track }
                .forEach { add(it) }
            if (size > CAPACITY) removeAt(CAPACITY)
        }
        write()
    }

    fun clear() {
        history.clear()
        write()
    }

    fun isEmpty() = history.isEmpty()

    private fun write() {
        sharedPreferences
            .edit()
            .putString(KEY, Gson().toJson(history))
            .apply()
    }

    private fun read(): Array<Track> {
        val json = sharedPreferences.getString(KEY, "[]")
        return Gson().fromJson(json, Array<Track>::class.java)
    }

    companion object {
        private const val CAPACITY = 10
        private const val KEY = "search_history"
    }
}
