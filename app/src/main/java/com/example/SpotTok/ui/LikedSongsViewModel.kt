package com.example.SpotTok.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.SpotTok.data.AppDatabase
import com.example.SpotTok.data.LikedSongsRepository
import com.example.SpotTok.data.SpotifyEntity
import kotlinx.coroutines.launch

/**
 * This is the ViewModel class that manages data for the UI related to bookmarked GitHub repos.
 */
class LikedSongsViewModel(application: Application): AndroidViewModel(application) {
    private val repository = LikedSongsRepository(
        AppDatabase.getInstance(application).spotifyDao()
    )

    /**
     * This property exposes the list of all bookmarked repos stored in the database, wrapped
     * within a LiveData object.  The list in the LiveData object is automatically updated
     * whenever the contents of the database changes.
     */
    val likedSongs = repository.getAllLikedSongs().asLiveData()

    fun addLikedSong(spotifyEntity: SpotifyEntity) {
        viewModelScope.launch {
            repository.insertLikedSong(spotifyEntity)
        }
    }


    fun removeLikedSong(spotifyEntity: SpotifyEntity) {
        viewModelScope.launch {
            repository.removeLikedSong(spotifyEntity)
        }
    }


   // fun getBookmarkedRepoByName(name: String) = repository.getBookmarkedRepoByName(name).asLiveData()
}