package com.example.githubsearchwithnavigation.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SpotifyEntity(
    @PrimaryKey
    val songName: String,

    val artistName: String
) : java.io.Serializable