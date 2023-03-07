package com.example.githubsearchwithnavigation.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity
data class GitHubRepo(
    @Json(name = "full_name")
    @PrimaryKey
    val name: String,

    @Json(name = "html_url")
    val url: String,

    val description: String?,

    @Json(name = "stargazers_count")
    val stars: Int
) : java.io.Serializable
