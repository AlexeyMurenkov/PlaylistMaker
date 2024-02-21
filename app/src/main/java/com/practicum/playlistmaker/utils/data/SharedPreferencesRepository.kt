package com.practicum.playlistmaker.utils.data

import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferencesRepository<T>(
    private val classOfT: Class<T>,
    val sharedPreferences: SharedPreferences,
    private val key: String
) {
    var storage: T
        get() {
            val value = sharedPreferences.getString(key, "{}")
            return Gson().fromJson(value, classOfT)
        }
        set(value) {
            sharedPreferences
                .edit()
                .putString(key, Gson().toJson(value))
                .apply()
        }
}
