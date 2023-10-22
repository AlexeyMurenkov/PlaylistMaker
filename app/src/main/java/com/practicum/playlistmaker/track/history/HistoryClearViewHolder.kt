package com.practicum.playlistmaker.track.history

import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.practicum.playlistmaker.R

class HistoryClearViewHolder(view: View, onClickListener: OnClickListener) : ViewHolder(view) {
    private val clearButton: Button = view.findViewById(R.id.search_history_clear)

    init {
        clearButton.setOnClickListener(onClickListener)
    }
}