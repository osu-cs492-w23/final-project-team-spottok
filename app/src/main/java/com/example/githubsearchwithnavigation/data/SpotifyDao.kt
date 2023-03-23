package com.example.githubsearchwithnavigation.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SpotifyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(spotifyEntity: SpotifyEntity)
    @Delete
    suspend fun delete(spotifyEntity: SpotifyEntity)

    @Query("SELECT * FROM SpotifyEntity")
    fun getAllSongs(): Flow<List<SpotifyEntity>>

}