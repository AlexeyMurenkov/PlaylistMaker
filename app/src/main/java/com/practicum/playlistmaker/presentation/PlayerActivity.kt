package com.practicum.playlistmaker.presentation

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.presentation.SearchActivity.Companion.KEY_PLAYER_TRACK
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.domain.models.Track
import com.practicum.playlistmaker.utils.dpToPx
import com.practicum.playlistmaker.utils.formatTrackTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlayerActivity : AppCompatActivity() {

    private var mediaPlayer = MediaPlayer()
    private var playerState = PlayerState.DEFAULT

    private val handler = Handler(Looper.getMainLooper())
    private val positionUpdater = Runnable { updatePosition() }


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
            playerPlay.setOnClickListener {
                playPause()
            }

            playerBack.setOnClickListener {
                finish()
            }
        }
        bind(track!!)
        preparePlayer(track)
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
        if (playerState == PlayerState.PLAYING) {
            playPause()
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

    private fun updatePosition() {
        if (mediaPlayer.currentPosition >= mediaPlayer.duration) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
            playerState = PlayerState.PAUSED
            setButtonState()
        }
        binding.playerProgress.text = formatTrackTime(mediaPlayer.currentPosition)
        if (playerState == PlayerState.PLAYING) {
            handler.postDelayed(positionUpdater, REFRESH_POSITION_PERIOD)
        }
    }

    private fun setButtonState() {
        if (playerState == PlayerState.PLAYING) {
            binding.playerPlay.setImageResource(R.drawable.button_pause)
            updatePosition()
        } else {
            binding.playerPlay.setImageResource(R.drawable.button_play)
            handler.removeCallbacks(positionUpdater)
        }
        binding.playerPlay.isEnabled = playerState != PlayerState.DEFAULT
    }

    private fun preparePlayer(track: Track) {
        if (track.previewUrl.isNullOrBlank()) return
        with(mediaPlayer) {
            setDataSource(track.previewUrl)
            setOnPreparedListener {
                playerState = PlayerState.PREPARED
                setButtonState()
                updatePosition()
            }
            prepareAsync()
        }
    }

    private fun playPause() {
        with(mediaPlayer) {
            if (playerState == PlayerState.PLAYING) {
                pause()
                playerState = PlayerState.PAUSED
            } else {
                start()
                playerState = PlayerState.PLAYING
            }
        }
        setButtonState()
        updatePosition()
    }

    companion object {
        private const val REFRESH_POSITION_PERIOD = 1000L
    }
}