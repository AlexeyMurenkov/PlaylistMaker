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
    val artworkUrl100: String,
    val previewUrl: String?
) : Serializable {
    fun getArtworkUrl512() = artworkUrl100.replaceAfterLast('/', POSTFIX_512_COVER)

    companion object {
        private const val POSTFIX_512_COVER = "512x512bb.jpg"
    }
}
