package com.practicum.playlistmaker.media.data.converters

import com.practicum.playlistmaker.media.data.db.TrackEntity
import com.practicum.playlistmaker.search.domain.models.Track
import java.time.Instant

class TrackDbConverter {
    fun map(track: Track) = with(track) {
        TrackEntity(
            id = trackId,
            addOrder = Instant.now().toEpochMilli(),
            trackName = trackName,
            artworkUrl100 = artworkUrl100,
            artistName = artistName,
            collectionName = collectionName?:"",
            releaseDate = releaseDate?:"",
            country = country,
            trackTimeMillis = trackTimeMillis,
            previewUrl = previewUrl?:""
        )
    }

    fun map(trackEntity: TrackEntity) = with(trackEntity) {
        Track(
            trackId = id,
            collectionName = collectionName,
            primaryGenreName = "",
            country = country,
            trackName = trackName,
            releaseDate = releaseDate,
            artistName = artistName,
            trackTimeMillis = trackTimeMillis,
            artworkUrl100 = artworkUrl100,
            previewUrl = previewUrl
        )
    }
}
