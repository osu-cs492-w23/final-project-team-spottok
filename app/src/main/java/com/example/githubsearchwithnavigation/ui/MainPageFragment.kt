package com.example.githubsearchwithnavigation.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchwithnavigation.R
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Track
import android.view.View.OnScrollChangeListener
import com.example.githubsearchwithnavigation.data.*
import kotlinx.coroutines.launch


class MainPageFragment: Fragment(R.layout.main_page_fragment) {
    private val TAG = "MainActivity"

//    private val repoListAdapter = SongAdapter(::onSongClick)
    private val viewModel: MainPageViewModel by viewModels()

    private lateinit var searchResultsListRV: RecyclerView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator
    private lateinit var songItemTitle: TextView
    private lateinit var songItemArtist: TextView

    private val clientId = "ef5c3cd0164544e28df769ab91ed8fdb"
    private val redirectUri = "http://com.spotify.android.githubsearchwithnavigation/callback"
    private var spotifyAppRemote: SpotifyAppRemote? = null

    // PLAYLISTS TO SHUFFLE BASED ON USER SETTINGS

    //

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val skipNextBtn: Button = view.findViewById(R.id.btn_skip_next)
        val skipPrevBtn: Button = view.findViewById(R.id.btn_skip_prev)
        val likeSongBtn: Button = view.findViewById(R.id.btn_like_song)

        searchErrorTV = view.findViewById(R.id.tv_search_error)
        loadingIndicator = view.findViewById(R.id.loading_indicator)

        /*
         * Set up RecyclerView.
         */
        songItemTitle = view.findViewById(R.id.tv_song_title)
        songItemArtist = view.findViewById(R.id.tv_song_artist)

        searchResultsListRV = view.findViewById(R.id.rv_search_results)
        searchResultsListRV.layoutManager = LinearLayoutManager(requireContext())
        searchResultsListRV.setHasFixedSize(true)
//        searchResultsListRV.adapter = repoListAdapter

        /*
         * Set up an observer on the current search results.  Every time the search results change,
         * send the new search results into the RecyclerView adapter to be displayed.
         */
//        viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
//            repoListAdapter.updatePlaylist(searchResults)
//        }

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

//        viewModel.loadSearchResults(sort!!)
        searchResultsListRV.scrollToPosition(0)

        skipNextBtn.setOnClickListener() {
            spotifyAppRemote?.let {
                it.playerApi.skipNext()
                it.playerApi.subscribeToPlayerState().setEventCallback {
                    val track: Track = it.track
                    Log.d("MainActivity", track.name + " by " + track.artist.name)

                    songItemTitle.text = track.name
                    songItemArtist.text = track.artist.name
                }
            }
        }

        skipPrevBtn.setOnClickListener() {
            spotifyAppRemote?.let {
                it.playerApi.skipPrevious()
                it.playerApi.subscribeToPlayerState().setEventCallback {
                    val track: Track = it.track
                    Log.d("MainActivity", track.name + " by " + track.artist.name)

                    songItemTitle.text = track.name
                    songItemArtist.text = track.artist.name
                }
            }
        }

        likeSongBtn.setOnClickListener() {
            val likedSong = SpotifyEntity(songItemTitle.text as String,
                songItemArtist.text as String
            )

//            val repository = LikedSongsRepository(
//                AppDatabase.getInstance(application).spotifyDao()
//            )

//            viewModelScope.launch {
//                repository.insertLikedSong(likedSong)
//            }
        }

        val connectionParams = ConnectionParams.Builder(clientId)
            .setRedirectUri(redirectUri)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(requireContext(), connectionParams, object : Connector.ConnectionListener {
            override fun onConnected(appRemote: SpotifyAppRemote) {
                spotifyAppRemote = appRemote
                Log.d("MainActivity", "Connected! Yay!")
                // Now you can start interacting with App Remote
                connected()
            }

            override fun onFailure(throwable: Throwable) {
                Log.e("MainActivity", throwable.message, throwable)
                // Something went wrong when attempting to connect! Handle errors here
            }
        })
    }

//    override fun onStart() {
//        super.onStart()
//        val connectionParams = ConnectionParams.Builder(clientId)
//            .setRedirectUri(redirectUri)
//            .showAuthView(true)
//            .build()
//
//        SpotifyAppRemote.connect(requireContext(), connectionParams, object : Connector.ConnectionListener {
//            override fun onConnected(appRemote: SpotifyAppRemote) {
//                spotifyAppRemote = appRemote
//                Log.d("MainActivity", "Connected! Yay!")
//                // Now you can start interacting with App Remote
//                connected()
//            }
//
//            override fun onFailure(throwable: Throwable) {
//                Log.e("MainActivity", throwable.message, throwable)
//                // Something went wrong when attempting to connect! Handle errors here
//            }
//        })
//    }

    private fun connected() {
        spotifyAppRemote?.let {
            val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val playlistURI = "spotify:playlist:" + prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default))

            // Play a playlist
            it.playerApi.setShuffle(true)
            it.playerApi.play(playlistURI)
            // Subscribe to PlayerState
            it.playerApi.subscribeToPlayerState().setEventCallback {
                val track: Track = it.track
                Log.d("MainActivity", track.name + " by " + track.artist.name)

                songItemTitle.text = track.name
                songItemArtist.text = track.artist.name
            }
        }
    }

    override fun onStop() {
        super.onStop();
        spotifyAppRemote?.let {
            SpotifyAppRemote.disconnect(it)
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_bookmarks -> {
                val intent = Intent(this, LikedSongsFragment::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action: Int = MotionEventCompat.getActionMasked(event)

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "Action was DOWN")
                true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "Action was MOVE")
                true
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "Action was UP")
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "Action was CANCEL")
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.d(TAG, "Movement occurred outside bounds of current screen element")
                true
            }
            else -> super.onTouchEvent(event)
        }
    }*/
}