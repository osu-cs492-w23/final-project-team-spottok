package com.example.githubsearchwithnavigation.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SpotifyDao {

    @Insert
    suspend fun insert(trackInfo: TrackInfo)

    @Delete
    suspend fun delete(trackInfo: TrackInfo)

    @Query("SELECT * FROM TrackInfo")
    fun getAllSongs(): Flow<List<TrackInfo>>

}