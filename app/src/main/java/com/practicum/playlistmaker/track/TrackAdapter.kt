package com.practicum.playlistmaker.track

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.utils.dpToPx

class TrackAdapter(
    private val tracks: List<Track>
) : Adapter<TrackViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val imageCornersPx = parent
            .getResources()
            .getDimension(R.dimen.dimen_2)

        val imageCornersDp = dpToPx(imageCornersPx, parent.context)

        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.card_track, parent,false)
        return TrackViewHolder(view, imageCornersDp)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}