package com.example.SpotTok.api

import com.example.SpotTok.data.SpotifyPlaylistResults
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyService {

    // hard code ES market to make parsing through JSON easier.
    // hard code to only have name, album, and artist

    @GET("playlists/{playlist_id}/tracks")
    suspend fun searchPlaylist(
        @Path("playlist_id") playlist_id: String,
        @Query("market") market: String? = "ES",
        @Query("fields") fields: String? = "items(added_by.id,href,track(name,album(name)))",
        @Query("limit") limit: String? = "15"
    ) : Response<SpotifyPlaylistResults>

    companion object {
        private const val BASE_URL = "https://api.spotify.com/v1/"
        private const val API_TOKEN = "BQBcDCYfTYmG7n6LPDP5ZKyTTYc76Dr8v9iDEJCkjRgvCwHGIsN7fXPVNIM440E-H424yLniFUS1wyuB4jwBDoRzk-M_ufDoyhO4yrXdhrIAtGnflI_BTtAoHgmFbJQ_gPyHYPtutZOQg4gDE-UaCe-jEcyinFsy_bf3QE8cmldYldMDdv_B-4Va7A"

        fun create() : SpotifyService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SpotifyService::class.java)
        }
    }
}