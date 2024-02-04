package com.practicum.playlistmaker.search.ui.track.history

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.search.ui.track.TrackAdapter
import java.util.function.BiConsumer

class HistoryTrackAdapter(
    tracks: List<Track>,
    onTrackClick: BiConsumer<Track, Int>,
    val onClearClick: OnClickListener
) : TrackAdapter(tracks, onTrackClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == VIEW_TYPE_BUTTON) {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.search_clear, parent, false)
            return HistoryClearViewHolder(view, onClearClick)
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int) =
        if (position == super.getItemCount()) VIEW_TYPE_BUTTON else super.getItemViewType(position)

    companion object {
        private const val VIEW_TYPE_BUTTON = 1
    }
}