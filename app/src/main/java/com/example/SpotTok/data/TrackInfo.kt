package com.example.SpotTok.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackInfo(
    @Json(name = "album")
    val album: AlbumInfo,

    @Json(name = "href")
    val href: String,

    @Json(name = "name")
    val songName: String
) : java.io.Serializable
