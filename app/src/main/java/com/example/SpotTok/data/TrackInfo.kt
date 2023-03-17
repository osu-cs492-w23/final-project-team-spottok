package com.example.SpotTok.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackInfo(
    // gets the id ONLY in string format
    @Json(name = "id")
    val id: String,

    // grabs the external url link, which will go to the link to open the song on Spotify
    @Json(name = "external_urls")
    val externalUrl: ExternalUrl,

    // gets the spotify:track:TRACK_ID
    @Json(name = "uri")
    val uri: String,

    // gets the song name in full with features
    @Json(name = "name")
    val songName: String,

    // grabs a list of the artists information on the current track
    @Json(name = "artists")
    val artistList: List<ArtistsInfo>
) : java.io.Serializable
