package com.practicum.playlistmaker.search.data.impl

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.data.TrackRepository
import com.practicum.playlistmaker.search.data.dto.TracksRequest
import com.practicum.playlistmaker.search.data.dto.TracksResponse
import com.practicum.playlistmaker.search.data.network.NetworkClient
import com.practicum.playlistmaker.search.data.network.impl.ITunesNetworkClient.Companion.HTTP_OK
import com.practicum.playlistmaker.search.domain.models.Track

class TrackRepositoryImpl(private val context: Context, private val networkClient: NetworkClient) :
    TrackRepository {
    override fun search(expression: String): Result<List<Track>> {
        return runCatching {
            val response = networkClient.doRequest(TracksRequest(expression)) as TracksResponse
            if (response.resultCode == HTTP_OK)
                response.results.map {
                    with(it) {
                        Track(
                            trackId,
                            collectionName,
                            primaryGenreName,
                            country,
                            trackName,
                            releaseDate,
                            artistName,
                            trackTimeMillis,
                            artworkUrl100,
                            previewUrl
                        )
                    }
                }
            else throw Exception()
        }
    }

    override fun play(track: Track) {
        context.startActivity(
            Intent(context, PlayerActivity::class.java)
                .putExtra(Intent.ACTION_ATTACH_DATA, track)
        )
    }
}
