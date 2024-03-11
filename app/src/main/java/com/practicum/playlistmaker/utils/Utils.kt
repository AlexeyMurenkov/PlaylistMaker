package com.practicum.playlistmaker.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

private const val TRACK_TIME_FORMAT = "mm:ss"

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}

fun switchTheme(isDarkTheme: Boolean) {
    AppCompatDelegate.setDefaultNightMode(
        if (isDarkTheme) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
    )
}

fun formatTrackTime(millis: Int): String =
    SimpleDateFormat(TRACK_TIME_FORMAT, Locale.getDefault()).format(millis)

fun <T> debounce(
    delay: Long,
    coroutineScope: CoroutineScope,
    useLast: Boolean,
    action: (T) -> Unit
): (T) -> Unit {
    var job: Job? = null
    return { p: T ->
        if (useLast) {
            job?.cancel()
        }
        if (job?.isCompleted != false || useLast) {
            job = coroutineScope.launch {
                delay(delay)
                action(p)
            }
        }
    }
}
