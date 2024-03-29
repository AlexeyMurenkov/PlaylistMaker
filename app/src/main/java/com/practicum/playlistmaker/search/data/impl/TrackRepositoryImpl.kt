package com.practicum.playlistmaker.search.data.impl

import android.content.Context
import android.content.Intent
import com.practicum.playlistmaker.player.ui.PlayerActivity
import com.practicum.playlistmaker.search.domain.TrackRepository
import com.practicum.playlistmaker.search.data.dto.TracksRequest
import com.practicum.playlistmaker.search.data.dto.TracksResponse
import com.practicum.playlistmaker.search.data.network.NetworkClient
import com.practicum.playlistmaker.search.data.network.impl.ITunesNetworkClient.Companion.HTTP_OK
import com.practicum.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(private val context: Context, private val networkClient: NetworkClient) :
    TrackRepository {
    override fun search(expression: String): Flow<Result<List<Track>>> {
        return flow {
            emit(
                runCatching {
                    val response =
                        networkClient.doRequest(TracksRequest(expression)) as TracksResponse
                    if (response.resultCode == HTTP_OK) {
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
                    } else throw Exception()
                }
            )
        }
    }

    override fun play(track: Track) {
        context.startActivity(
            Intent(context, PlayerActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(Intent.ACTION_ATTACH_DATA, track)
        )
    }
}
