package com.example.SpotTok.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SpotifyEntity(
    @PrimaryKey
    val songName: String,

    val albumName: String
) : java.io.Serializable