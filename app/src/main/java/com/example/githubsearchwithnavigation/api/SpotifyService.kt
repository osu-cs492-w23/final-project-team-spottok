package com.example.githubsearchwithnavigation.api

import com.example.githubsearchwithnavigation.data.SpotifyPlaylistResults
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
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
        @Query("limit") limit: String? = "15",
        @Header("Authorization: ") API_TOKEN: String
    ) : Response<SpotifyPlaylistResults>

    companion object {
        private const val BASE_URL = "https://api.spotify.com/v1/"
        private const val API_TOKEN = "BQDYMdBHF9ujb5GmrtmUlThF3VmH1bOdf3xkwtXhrwMzuw-ocxjE0OypMX7-wzOrWaGU0ILETRUsRF-u_RrBPiWOOUGUintmn8V5_FTRqk_sdOyDs0g50KbXJ5UHfDV9A6rIgHU9EnTqPmqcBASx5c6WRblqt6Pa_GRIVTyotsmvR15CWIM"
        fun create() : SpotifyService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(SpotifyService::class.java)
        }
    }
}