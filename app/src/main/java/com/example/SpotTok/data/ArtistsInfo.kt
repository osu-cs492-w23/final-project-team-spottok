package com.example.SpotTok.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtistsInfo(
    // this will grab the name of the artist from the list of artists
    @Json(name = "name")
    val artistName: String
): java.io.Serializable
