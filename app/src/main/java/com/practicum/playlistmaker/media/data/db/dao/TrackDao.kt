package com.practicum.playlistmaker.media.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.practicum.playlistmaker.media.data.db.TrackEntity

@Dao
interface TrackDao {
    @Insert
    suspend fun add(track: TrackEntity)

    @Delete
    suspend fun remove(track: TrackEntity)

    @Query("SELECT * FROM favorites ORDER BY addOrder DESC;")
    fun getFavorites(): List<TrackEntity>

    @Query("SELECT COUNT(*) FROM favorites WHERE id = :trackId;")
    fun isFavorite(trackId: Long): Int

    @Query("SELECT id FROM favorites;")
    fun getFavoritesId(): List<Long>
}
