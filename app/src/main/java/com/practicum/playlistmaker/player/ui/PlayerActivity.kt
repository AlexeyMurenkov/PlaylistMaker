package com.practicum.playlistmaker.player.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.domain.models.PlayerScreenState
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.utils.dpToPx
import com.practicum.playlistmaker.utils.formatTrackTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlayerActivity : AppCompatActivity() {

    private val viewModel: PlayerViewModel by viewModel()
    private lateinit var binding: ActivityPlayerBinding
    private var track: Track? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        track = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Intent.ACTION_ATTACH_DATA, Track::class.java)
        } else {
            intent.getSerializableExtra(Intent.ACTION_ATTACH_DATA) as Track?
        }
        if (track == null) finish()

        binding = ActivityPlayerBinding.inflate(layoutInflater)

        with(binding) {
            setContentView(root)
            playerPlay.isEnabled = false
            playerPlay.setOnClickListener { viewModel.playPause() }
            playerFavorites.setOnClickListener { viewModel.changeFavorite(track!!) }
            playerBack.setNavigationOnClickListener { finish() }
        }
        bind(track!!)

        viewModel.preparePlayer(track!!)
        viewModel.playerScreenState.observe(this) {
            when (it) {
                is PlayerScreenState.Progress -> setProgress(it)
                is PlayerScreenState.Favorite -> setFavorite(it)
            }
        }
    }

    private fun setProgress(progress: PlayerScreenState.Progress) {
        setPlayButtonState(progress.state)
        setPlayerProgress(
            when (progress.state) {
                PlayerState.PLAYING, PlayerState.PAUSED -> progress.position
                else -> 0
            }
        )
    }

    private fun setFavorite(favorite: PlayerScreenState.Favorite) {
        binding.playerFavorites.setImageResource(
            if (favorite.isFavorite)
                R.drawable.remove_from_favorites
            else
                R.drawable.add_to_favorites
        )
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

    override fun onPause() {
        super.onPause()
        viewModel.pause()
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

    private fun setPlayButtonState(playerState: PlayerState) {
        binding.playerPlay.isEnabled = true
        when (playerState) {
            PlayerState.PLAYING -> binding.playerPlay.setImageResource(R.drawable.button_pause)
            PlayerState.PREPARED, PlayerState.PAUSED -> binding.playerPlay.setImageResource(R.drawable.button_play)
            else -> binding.playerPlay.isEnabled = false
        }
    }

    private fun setPlayerProgress(progress: Int) {
        binding.playerProgress.text = formatTrackTime(progress)
    }
}
