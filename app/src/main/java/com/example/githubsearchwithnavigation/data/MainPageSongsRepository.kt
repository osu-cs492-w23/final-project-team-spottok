package com.example.githubsearchwithnavigation.data

import android.util.Log
import com.example.githubsearchwithnavigation.api.SpotifyService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * THIS CLASS IS USED TO LOAD THE SONGS ONTO THE MAIN PAGE FROM DATABASE
 * CAN BE BLAKE OR JOHN WORKING ON THIS
 * (WILL LOAD CURRENT SONG ONTO MAIN PAGE)
 *  I STARTED TO CATER THE CODE TO OUR CURRENT FINAL, BUT YOU CAN FINISH IT -JASON
 */
class MainPageSongsRepository(
    private val service: SpotifyService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun loadPlaylist(
        playlist_id: String,
        market: String?,
        fields: String?,
        limit: String?
    ): Result<SpotifyPlaylistResults> =

        withContext(dispatcher) {
            try {
                val response = service.searchPlaylist(
                    playlist_id, market, fields, limit
                )
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception(response.errorBody()?.string()))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

//    private fun buildGitHubQuery(
//        query: String,
//        user: String?,
//        languages: Set<String>?,
//        firstIssues: Int
//    ) : String {
//        /*
//         * e.g. "android user:square language:kotlin language:java good-first-issues:>=2"
//         */
//        var fullQuery = query
//        if (!TextUtils.isEmpty(user)) {
//            fullQuery += " user:$user"
//        }
//        if (languages != null && languages.isNotEmpty()) {
//            fullQuery += languages.joinToString(separator = " language:", prefix = " language:")
//        }
//        if (firstIssues > 0) {
//            fullQuery += " good-first-issues:>=$firstIssues"
//        }
//        return fullQuery
//    }
}