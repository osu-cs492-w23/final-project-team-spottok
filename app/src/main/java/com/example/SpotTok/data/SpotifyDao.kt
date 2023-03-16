package com.example.SpotTok.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SpotifyDao {

    @Insert
    suspend fun insert(spotifyEntity: SpotifyEntity)
    @Delete
    suspend fun delete(spotifyEntity: SpotifyEntity)

    @Query("SELECT * FROM SpotifyEntity")
    fun getAllSongs(): Flow<List<SpotifyEntity>>

}