package com.practicum.playlistmaker.data

import com.practicum.playlistmaker.data.dto.TracksRequest
import com.practicum.playlistmaker.data.dto.TracksResponse
import com.practicum.playlistmaker.data.network.ITunesNetworkClient.Companion.HTTP_OK
import com.practicum.playlistmaker.domain.api.TrackRepository
import com.practicum.playlistmaker.domain.models.Storage
import com.practicum.playlistmaker.domain.models.Track

class TrackRepositoryImpl(val networkClient: NetworkClient) : TrackRepository {
    override fun searchTracks(expression: String): Storage<List<Track>> {
        return try {
            val response = networkClient.doRequest(TracksRequest(expression))
            if (response.resultCode == HTTP_OK) {
                val tracks = (response as TracksResponse).results.map {
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
                Storage(tracks, false)
            } else {
                Storage(emptyList(), true)
            }
        } catch (e: Exception) {
            Storage(emptyList(), true)
        }
    }
}