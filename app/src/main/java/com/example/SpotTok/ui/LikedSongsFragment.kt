package com.example.SpotTok.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SpotTok.data.Tracks
import com.example.githubsearchwithnavigation.R

class LikedSongsFragment : Fragment(R.layout.liked_songs_fragment) {
    private val viewModel:LikedSongsViewModel by viewModels()

    // DO WE EVEN NEED THIS ADAPTER?
    private val allSongAdapter = SongAdapter(::onLikedSongClick)

    private lateinit var likedSongsRV: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
         * Set up RecyclerView
         */
        likedSongsRV = view.findViewById(R.id.rv_bookmarked_repos)
        likedSongsRV.layoutManager = LinearLayoutManager(requireContext())
        likedSongsRV.setHasFixedSize(true)
        likedSongsRV.adapter = allSongAdapter

        viewModel.likedSongs.observe(viewLifecycleOwner) {
            // display the recycler view of the list of bookmarked songs in the database here


// ASK HESS WHAT TO DO HERE



        //            allSongAdapter.updatePlaylist(it)
        }
    }

    /**
     * This method is passed into the RecyclerView adapter to handle clicks on individual items
     * in the list of GitHub repos.  When a repo is clicked, a new activity is launched to view
     * details about that repo.
     */
    private fun onLikedSongClick(currTrack: Tracks) {
        // Display Snackbar of song info here
    }
}