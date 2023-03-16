package com.example.SpotTok.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumInfo(
    @Json(name = "name")
    val albumName: String
) : java.io.Serializable
