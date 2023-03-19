package com.example.githubsearchwithnavigation.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpotifyPlaylistResults(
    @Json(name = "items")
    val items: List<Tracks>
) : java.io.Serializable
