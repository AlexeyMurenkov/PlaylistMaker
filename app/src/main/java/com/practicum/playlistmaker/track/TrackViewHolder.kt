package com.practicum.playlistmaker.track

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.utils.dpToPx

class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView
    private val artist: TextView
    private val time: TextView
    private val image: ImageView

    init {
        name = view.findViewById(R.id.track_name)
        artist = view.findViewById(R.id.track_artist)
        time = view.findViewById(R.id.track_time)
        image = view.findViewById(R.id.track_image)
    }

    fun bind(track: Track) {
        name.text = track.trackName
        artist.text = track.artistName
        time.text = track.trackTime

        val imageCornersPx = itemView
            .getResources()
            .getDimension(R.dimen.dimen_2)

        val imageCornersDp = dpToPx(imageCornersPx, itemView.context)

        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.track_placeholder)
            .fitCenter()
            .transform(RoundedCorners(imageCornersDp))
            .into(image)
    }

}