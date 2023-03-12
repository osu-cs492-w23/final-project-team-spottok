package com.example.githubsearchwithnavigation.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class TrackInfo(
    @Json(name = "album")
    val album: AlbumInfo,

    @Json(name = "href")
    val href: String,

    @Json(name = "name")
    @PrimaryKey
    val songName: String
) : java.io.Serializable
