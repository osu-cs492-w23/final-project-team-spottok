package com.example.githubsearchwithnavigation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchwithnavigation.data.SpotifyEntity
import com.example.githubsearchwithnavigation.R
import com.google.android.material.snackbar.Snackbar

class LikedSongsFragment : Fragment(R.layout.liked_songs_fragment) {
    private val viewModel:LikedSongsViewModel by viewModels()

    // Adapter for JUST liked songs page
    private val allSongAdapter = LikedSongsAdapter(::onLikedSongClick)

    private lateinit var likedSongsRV: RecyclerView

    private val likedModel: LikedSongsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
         * Set up RecyclerView
         */
        likedSongsRV = view.findViewById(R.id.rv_liked_songs)
        likedSongsRV.layoutManager = LinearLayoutManager(requireContext())
        likedSongsRV.setHasFixedSize(true)
        likedSongsRV.adapter = allSongAdapter

        viewModel.likedSongs.observe(viewLifecycleOwner) {
            // display the recycler view of the list of bookmarked songs in the database here
            allSongAdapter.updateLikedSongList(it)
        }
    }


    private fun onLikedSongClick(spotifyEntity: SpotifyEntity): Unit {
        // Display Snackbar of song like feature here
        Snackbar.make(
            likedSongsRV,
            "You absolutely hate this garbage song",
            Snackbar.LENGTH_LONG
        ).show()

        likedModel.removeLikedSong(spotifyEntity)

    }
}