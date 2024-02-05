package com.practicum.playlistmaker.utils.data

import android.content.Context
import com.google.gson.Gson

class SharedPreferencesRepository<T>(
    context: Context,
    private val classOfT: Class<T>,
    nameSharedPreferences: String,
    private val key: String
) {

    private val sharedPreferences =
        context.getSharedPreferences(nameSharedPreferences, Context.MODE_PRIVATE)
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
