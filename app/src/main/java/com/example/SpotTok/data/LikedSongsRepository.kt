package com.example.SpotTok.data

class LikedSongsRepository(
    private val dao: SpotifyDao
) {
    suspend fun insertLikedSong(spotifyEntity: SpotifyEntity) = dao.insert(spotifyEntity)
    suspend fun removeLikedSong(spotifyEntity: SpotifyEntity) = dao.delete(spotifyEntity)
    fun getAllLikedSongs() = dao.getAllSongs()

}