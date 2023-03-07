package com.example.githubsearchwithnavigation.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * This class represents the entrypoint into the Room-based database for the app.  It is a
 * singleton class, whose single instance you can access through the `getInstance()` method.
 */
@Database(entities = [GitHubRepo::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun gitHubRepoDao(): GitHubRepoDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "bookmarkedRepos.db"
            ).build()

        /**
         * This method returns the singleton instance of the application database.  It creates the
         * instance (in a thread-safe way) if necessary.
         */
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }
    }
}