package com.practicum.playlistmaker

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmaker.track.Track
import java.util.LinkedList

class SearchHistory(
    val sharedPreferences: SharedPreferences
) {

    val history: LinkedList<Track> = LinkedList()

    init {
        val json = sharedPreferences.getString(KEY, "[]")
        Gson().fromJson(json, Array<Track>::class.java).forEach { history.add(it) }
    }

    fun add(item: Track) {
        with(history) {
            remove(item)
            addFirst(item)
            if (size > CAPACITY) removeLast()
        }
    }

    fun clear() {
        history.clear()
    }

    fun write() {
        sharedPreferences
            .edit()
            .putString(KEY, Gson().toJson(history))
            .apply()
    }

    fun isEmpty() = history.isEmpty()

    companion object {
        const val CAPACITY = 10
        const val KEY = "search_history"
    }
}
