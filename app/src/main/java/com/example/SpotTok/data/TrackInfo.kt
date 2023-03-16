package com.example.SpotTok.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackInfo(
    // gets the id ONLY in string format
    @Json(name = "id")
    val id: String,


    @Json(name = "external_urls")
    val externalUrl: ExternalUrl,

    // gets the spotify:track:TRACK_ID
    @Json(name = "uri")
    val uri: String,

    // gets the song name in full with features
    @Json(name = "name")
    val songName: String
) : java.io.Serializable
