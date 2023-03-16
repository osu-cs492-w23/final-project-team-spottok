package com.example.SpotTok.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchwithnavigation.R
import com.example.SpotTok.data.LoadingStatus
import com.example.SpotTok.data.TrackInfo
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainPageFragment: Fragment(R.layout.main_page_fragment) {
    private val TAG = "MainActivity"

    private val repoListAdapter = GitHubRepoListAdapter(::onSongLikeClick)
    private val viewModel: MainPageViewModel by viewModels()

    private lateinit var searchResultsListRV: RecyclerView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    // PLAYLISTS TO SHUFFLE BASED ON USER SETTINGS

    //



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        searchErrorTV = view.findViewById(R.id.tv_search_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        /*
         * Set up RecyclerView.
         */
        searchResultsListRV = view.findViewById(R.id.rv_search_results)
        searchResultsListRV.layoutManager = LinearLayoutManager(requireContext())
        searchResultsListRV.setHasFixedSize(true)
        searchResultsListRV.adapter = repoListAdapter

        /*
         * Set up an observer on the current search results.  Every time the search results change,
         * send the new search results into the RecyclerView adapter to be displayed.
         */
        viewModel.searchResults.observe(viewLifecycleOwner) {
                //searchResults ->
            //repoListAdapter.updateRepoList(searchResults)
        }

        /*
         * Set up an observer on the loading status of the API query.  Display the correct UI
         * elements based on the current loading status.
         */
        viewModel.loadingStatus.observe(viewLifecycleOwner) { loadingStatus ->
            when (loadingStatus) {
                LoadingStatus.LOADING -> {
                    loadingIndicator.visibility = View.VISIBLE
                    searchResultsListRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    searchResultsListRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.VISIBLE
                }
                else -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    searchResultsListRV.visibility = View.VISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
            }
        }

        /*
         * Set up an observer on the error message associated with the current API query.  If the
         * error message is not null, display it to the user.
         */
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage->
            if (errorMessage != null) {
                Log.d(TAG, "Error executing search query: $errorMessage")
                searchErrorTV.text = getString(R.string.search_error, errorMessage)
            }
        }

        /*
         * Attach click listener to "search" button to perform repository search with GitHub API
         * using the search query entered by the user.  Also use the values of the appropriate
         * settings to influence the API call.
         */
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        //val genre = prefs.getString(getString(R.string.pref_genre_key), null)
        //viewModel.loadSearchResults(/*enter in query parameters*/)
        searchResultsListRV.scrollToPosition(0)
    }


    private fun onSongLikeClick(song: TrackInfo) {
        //add track to likes
    }
}