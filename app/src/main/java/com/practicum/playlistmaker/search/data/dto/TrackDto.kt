package com.practicum.playlistmaker.search.data.dto

data class TrackDto(
    val trackId: Long,
    val collectionName: String?,
    val primaryGenreName: String,
    val country: String,
    val trackName: String,
    val releaseDate: String?,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String,
    val previewUrl: String?
)
