package com.example.githubsearchwithnavigation.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * This is the DAO that encodes queries over the bookmarked GitHub repos database.
 */
@Dao
interface GitHubRepoDao {
    /**
     * This method inserts a single GitHub repo into the database.
     */
    @Insert
    suspend fun insert(repo: GitHubRepo)

    /**
     * This method deletes the specified repo from the database.
     */
    @Delete
    suspend fun delete(repo: GitHubRepo)

    /**
     * This method fetches all bookmarked repos from the database.
     *
     * @return The list of bookmarked repos is returned wrapped within a Kotlin Flow object.
     */
    @Query("SELECT * FROM GitHubRepo")
    fun getAllRepos(): Flow<List<GitHubRepo>>

    /**
     * This method returns a single bookmarked repo from the database based on its name.
     *
     * @return The repo matching the specified `name`, wrapped in a Kotlin Flow.
     */
    @Query("SELECT * FROM GitHubRepo WHERE name = :name LIMIT 1")
    fun getRepoByName(name: String): Flow<GitHubRepo?>
}