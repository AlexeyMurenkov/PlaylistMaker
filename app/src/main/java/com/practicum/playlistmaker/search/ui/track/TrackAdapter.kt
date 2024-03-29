package com.practicum.playlistmaker.search.ui.track

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.utils.dpToPx
import java.util.function.BiConsumer

open class TrackAdapter(
    private val tracks: List<Track>,
    private val onTrackClick: BiConsumer<Track, Int>
) : Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageCornersPx = parent
            .resources
            .getDimension(R.dimen.dimen_2)

        val imageCornersDp = dpToPx(imageCornersPx, parent.context)

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_track, parent, false)

        return TrackViewHolder(view, imageCornersDp)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position >= tracks.size) return
        val track = tracks[position]
        if (holder is TrackViewHolder) {
            with(holder) {
                bind(track)
                itemView.setOnClickListener {
                    onTrackClick.accept(track, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}
