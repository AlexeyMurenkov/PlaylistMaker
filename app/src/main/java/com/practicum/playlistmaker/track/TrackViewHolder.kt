package com.practicum.playlistmaker.track

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R

class TrackViewHolder(view: View, val imageCornersDp: Int) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.track_name)
    private val artist: TextView = view.findViewById(R.id.track_artist)
    private val time: TextView = view.findViewById(R.id.track_time)
    private val image: ImageView = view.findViewById(R.id.track_image)

    fun bind(track: Track) {
        name.text = track.trackName
        artist.text = track.artistName
        time.text = track.trackTime

        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.track_placeholder)
            .fitCenter()
            .transform(RoundedCorners(imageCornersDp))
            .into(image)
    }

}