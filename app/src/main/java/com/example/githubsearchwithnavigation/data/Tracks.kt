package com.example.githubsearchwithnavigation.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tracks(
    @Json(name = "track")
    val tracks: TrackInfo
) : java.io.Serializable
