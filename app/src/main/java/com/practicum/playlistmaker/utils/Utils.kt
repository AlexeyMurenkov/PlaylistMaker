package com.practicum.playlistmaker.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

private const val TRACK_TIME_FORMAT = "mm:ss"

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}

fun switchTheme(checked: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (checked) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
    )
}

fun formatTrackTime(millis: Int) : String =
    SimpleDateFormat(TRACK_TIME_FORMAT, Locale.getDefault()).format(millis)
