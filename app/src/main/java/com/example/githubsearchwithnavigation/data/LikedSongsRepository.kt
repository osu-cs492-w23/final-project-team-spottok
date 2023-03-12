package com.example.githubsearchwithnavigation.data

class LikedSongsRepository(
    private val dao: SpotifyDao
) {
    suspend fun insertLikedSong(trackInfo: TrackInfo) = dao.insert(trackInfo)
    suspend fun removeLikedSong(trackInfo: TrackInfo) = dao.delete(trackInfo)
    fun getAllLikedSongs() = dao.getAllSongs()

}