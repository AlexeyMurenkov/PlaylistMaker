package com.practicum.playlistmaker.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.track.Track
import com.practicum.playlistmaker.utils.KEY_PLAYER_TRACK
import com.practicum.playlistmaker.utils.dpToPx
import com.practicum.playlistmaker.utils.formatTrackTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val track: Track? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(KEY_PLAYER_TRACK, Track::class.java)
        } else {
            intent.getSerializableExtra(KEY_PLAYER_TRACK) as Track?
        }
        if (track == null) finish()

        val binding = ActivityPlayerBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)

            bind(this, track!!)

            playerBack.setOnClickListener {
                finish()
            }
        }

    }

    private fun bind(binding: ActivityPlayerBinding, track: Track) {
        with(binding) {
            with(track) {
                loadTrackBigImage(playerCover, this)

                playerTrackName.text = trackName
                playerArtistName.text = artistName
                playerTrackTime.text = formatTrackTime(trackTimeMillis)
                playerReleaseYear.text = if (releaseDate.isNullOrBlank()) "" else LocalDate.parse(releaseDate, DateTimeFormatter.ISO_DATE_TIME).year.toString()
                playerGenreName.text = primaryGenreName
                playerCountryName.text = country
                with(playerCollectionName) {
                    if (collectionName.isNullOrBlank()) {
                        visibility = View.GONE
                    } else {
                        text = collectionName
                        visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun loadTrackBigImage(imageView: ImageView, track: Track) {
        val artworkUrl512 = track.artworkUrl100.replaceAfterLast('/', POSTFIX_BIG_COVER)

        Glide.with(this)
            .load(artworkUrl512)
            .placeholder(R.drawable.track_placeholder)
            .fitCenter()
            .transform(
                RoundedCorners(
                    dpToPx(resources.getDimension(R.dimen.dimen_2), this)
                )
            )
            .into(imageView)
    }

    companion object {
        private const val POSTFIX_BIG_COVER = "512x512bb.jpg"
    }
}