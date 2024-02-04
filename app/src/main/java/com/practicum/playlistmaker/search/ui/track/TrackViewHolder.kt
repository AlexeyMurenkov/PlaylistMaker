package com.practicum.playlistmaker.search.ui.track

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.utils.formatTrackTime

class TrackViewHolder(view: View, val imageCornersDp: Int) : ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.track_name)
    private val artist: TextView = view.findViewById(R.id.track_artist)
    private val time: TextView = view.findViewById(R.id.track_time)
    private val image: ImageView = view.findViewById(R.id.track_image)

    fun bind(track: Track) {
        name.text = track.trackName
        artist.text = track.artistName
        time.text = formatTrackTime(track.trackTimeMillis)

        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.track_placeholder)
            .fitCenter()
            .transform(RoundedCorners(imageCornersDp))
            .into(image)
    }
}