package com.practicum.playlistmaker.media.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class TrackEntity(
    @PrimaryKey val id: Long,
    val addOrder: Long,
    val trackName: String,
    val artworkUrl100: String,
    val artistName: String,
    val collectionName: String,
    val releaseDate: String,
    val country: String,
    val trackTimeMillis: Int,
    val previewUrl: String
)
