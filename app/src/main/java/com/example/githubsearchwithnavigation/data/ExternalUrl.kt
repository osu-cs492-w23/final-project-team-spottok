package com.example.githubsearchwithnavigation.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ExternalUrl(
    // gets the full link to open the song link on spotify
    @Json(name = "spotify")
    val spotifyUrl: String
) : java.io.Serializable
