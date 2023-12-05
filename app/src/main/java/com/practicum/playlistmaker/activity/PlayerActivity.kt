package com.practicum.playlistmaker.activity

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
import com.practicum.playlistmaker.activity.SearchActivity.Companion.KEY_PLAYER_TRACK
import com.practicum.playlistmaker.databinding.ActivityPlayerBinding
import com.practicum.playlistmaker.track.Track
import com.practicum.playlistmaker.utils.dpToPx
import com.practicum.playlistmaker.utils.formatTrackTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PlayerActivity : AppCompatActivity() {

    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT

    private val handler = Handler(Looper.getMainLooper())
    private val positionUpdater = Runnable { positionUpdate() }


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
        if (playerState == STATE_PLAYING) {
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

    private fun positionUpdate() {
        if (mediaPlayer.currentPosition >= mediaPlayer.duration) {
            mediaPlayer.pause()
            mediaPlayer.seekTo(0)
            playerState = STATE_PAUSED
            setButtonState()
        }
        binding.playerProgress.text = formatTrackTime(mediaPlayer.currentPosition)
        if (playerState == STATE_PLAYING) {
            handler.postDelayed(positionUpdater, 1000L)
        }
    }
    private fun setButtonState() {
        if (playerState == STATE_PLAYING) {
            binding.playerPlay.setImageResource(R.drawable.button_pause)
            positionUpdate()
        } else {
            binding.playerPlay.setImageResource(R.drawable.button_play)
            handler.removeCallbacks(positionUpdater)
        }
        binding.playerPlay.isEnabled = playerState != STATE_DEFAULT
    }

    private fun preparePlayer(track: Track) {
        if (track.previewUrl.isNullOrBlank()) return
        with(mediaPlayer) {
            setDataSource(track.previewUrl)
            setOnPreparedListener {
                playerState = STATE_PREPARED
                setButtonState()
                positionUpdate()
            }
            prepareAsync()
        }
    }

    private fun playPause() {
        with(mediaPlayer) {
            if (playerState == STATE_PLAYING) {
                pause()
                playerState = STATE_PAUSED
            } else {
                start()
                playerState = STATE_PLAYING
            }
        }
        setButtonState()
        positionUpdate()
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}