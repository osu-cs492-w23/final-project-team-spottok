package com.example.SpotTok.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.SpotTok.api.SpotifyService
import com.example.SpotTok.data.MainPageSongsRepository
import com.example.SpotTok.data.LoadingStatus
import com.example.SpotTok.data.SpotifyPlaylistResults
import com.example.SpotTok.data.Tracks
import kotlinx.coroutines.launch


/**
 * THIS WILL BE THE VIEW MODEL FOR OUR MAIN PAGE
 * IT WILL LOAD THE SONG, ALBUM NAME, PLAY/PAUSE, AND ALBUM PICTURE
 */



class MainPageViewModel: ViewModel() {
    private val repository = MainPageSongsRepository(SpotifyService.create())

    /*
     * The most recent search results from the GitHub API are stored in this private property.
     * These search results are exposed to the outside world in immutable form via the public
     * `searchResults` property below.
     */
    private val _searchResults = MutableLiveData<SpotifyPlaylistResults?>(null)

    /**
     * This value provides the most recent search results returned from the GitHub API.  It is
     * null if there are no current search results (e.g. in the case of an error).
     */
    val searchResults: LiveData<SpotifyPlaylistResults?> = _searchResults

    /*
     * The current loading state is stored in this private property.  This loading state is exposed
     * to the outside world in immutable form via the public `loadingStatus` property below.
     */
    private val _loadingStatus = MutableLiveData<LoadingStatus>(LoadingStatus.SUCCESS)

    /**
     * This property indicates the current loading state of an API query.  It is `LOADING` if an
     * API query is currently being executed, `SUCCESS` of the most recent API query was
     * successful, and `ERROR` if the most recent API query resulted in an error.
     */
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    /*
     * The current error message for the most recent API query is stored in this private property.
     * This error message is exposed to the outside world in immutable form via the public
     * `errorMessage` property below.
     */
    private val _errorMessage = MutableLiveData<String?>(null)

    /**
     * This property provides the error message associated with the most recent API query, if
     * there is one.  If there was no error associated with the most recent API query, it will be
     * null.
     */
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * This method triggers a new search query to the GitHub API's "search repositories" method.
     * It updates the public properties of this ViewModel class to reflect the current status
     * of the API query.
     *
     * @param query The search query term to send to the GitHub API.
     * @param sort The sorting option to send to the GitHub API.
     * @param user A specific GitHub user/organization to which to limit search results.
     * @param languages A set of programming languages to which to limit search results.
     * @param firstIssues The minimum number of "good first issues" to which to limit search
     *   results.
     */
    fun loadSearchResults(
        query: String,
        sort: String?,
    ) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            _errorMessage.value = null
            val result = repository.loadPlaylist(query, sort, null, null)
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
            _searchResults.value = result.getOrNull()
            _errorMessage.value = result.exceptionOrNull()?.message
        }
    }
}