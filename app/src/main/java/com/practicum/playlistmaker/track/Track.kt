package com.practicum.playlistmaker.track

import java.io.Serializable

data class Track(
    val trackId: Long,
    val collectionName: String?,
    val primaryGenreName: String,
    val country: String,
    val trackName: String,
    val releaseDate: String?,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String
) : Serializable
