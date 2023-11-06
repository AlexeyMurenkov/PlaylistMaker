package com.practicum.playlistmaker.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.activity.SearchActivity.Companion.KEY_PLAYER_TRACK
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.track.Track
import com.practicum.playlistmaker.utils.dpToPx
import com.practicum.playlistmaker.utils.formatTrackTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val track: Track? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(KEY_PLAYER_TRACK, Track::class.java)
        } else {
            intent.getSerializableExtra(KEY_PLAYER_TRACK) as Track?
        }
        if (track == null) finish()

        binding = ActivityPlayerBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)
            playerBack.setOnClickListener {
                finish()
            }
        }
        bind(track!!)
    }

    private fun bind(track: Track) {
        loadTrackBigImage(track)

        with(binding) {
            playerTrackName.text = track.trackName
            playerArtistName.text = track.artistName
            playerTrackTime.text = formatTrackTime(track.trackTimeMillis)
            playerReleaseYear.text = if (track.releaseDate.isNullOrBlank()) "" else LocalDate.parse(
                track.releaseDate,
                DateTimeFormatter.ISO_DATE_TIME
            ).year.toString()
            playerGenreName.text = track.primaryGenreName
            playerCountryName.text = track.country
            if (track.collectionName.isNullOrBlank()) {
                playerCollectionName.visibility = View.GONE
            } else {
                playerCollectionName.text = track.collectionName
                playerCollectionName.visibility = View.VISIBLE
            }
        }
    }

    private fun loadTrackBigImage(track: Track) {
        Glide.with(this)
            .load(track.getArtworkUrl512())
            .placeholder(R.drawable.track_placeholder)
            .fitCenter()
            .transform(
                RoundedCorners(
                    dpToPx(resources.getDimension(R.dimen.dimen_2), this)
                )
            )
            .into(binding.playerCover)
    }
}