package com.practicum.playlistmaker.player.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.player.domain.models.PlayerScreenState
import com.practicum.playlistmaker.search.domain.models.Track
import com.practicum.playlistmaker.player.domain.models.PlayerState
import com.practicum.playlistmaker.utils.dpToPx
import com.practicum.playlistmaker.utils.formatTrackTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlayerActivity : AppCompatActivity() {

    lateinit var viewModel: PlayerViewModel
    lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val track: Track? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(Intent.ACTION_ATTACH_DATA, Track::class.java)
        } else {
            intent.getSerializableExtra(Intent.ACTION_ATTACH_DATA) as Track?
        }
        if (track == null) finish()

        binding = ActivityPlayerBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory(track!!)
        )[PlayerViewModel::class.java]

        with(binding) {
            setContentView(root)
            playerPlay.isEnabled = false
            playerPlay.setOnClickListener { viewModel.playPause() }
            playerBack.setOnClickListener { finish() }
        }
        bind(track)

        viewModel.preparePlayer()
        viewModel.getPlayerScreenState().observe(this) {
            when (it) {
                is PlayerScreenState.State -> setButtonState(it.state)
                is PlayerScreenState.Position -> setPlayerProgress(it.position)
            }
        }
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

    private fun setButtonState(playerState: PlayerState) {
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