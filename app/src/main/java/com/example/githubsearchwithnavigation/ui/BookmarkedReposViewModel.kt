package com.example.githubsearchwithnavigation.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubsearchwithnavigation.data.AppDatabase
import com.example.githubsearchwithnavigation.data.BookmarkedReposRepository
import com.example.githubsearchwithnavigation.data.GitHubRepo
import kotlinx.coroutines.launch

/**
 * This is the ViewModel class that manages data for the UI related to bookmarked GitHub repos.
 */
class BookmarkedReposViewModel(application: Application): AndroidViewModel(application) {
    private val repository = BookmarkedReposRepository(
        AppDatabase.getInstance(application).gitHubRepoDao()
    )

    /**
     * This property exposes the list of all bookmarked repos stored in the database, wrapped
     * within a LiveData object.  The list in the LiveData object is automatically updated
     * whenever the contents of the database changes.
     */
    val bookmarkedRepos = repository.getAllBookmarkedRepos().asLiveData()

    /**
     * This method is used to trigger an insertion of a GitHub repo into the bookmarked repos
     * database.  The operation runs in the background in a coroutine.
     */
    fun addBookmarkedRepo(repo: GitHubRepo) {
        viewModelScope.launch {
            repository.insertBookmarkedRepo(repo)
        }
    }

    /**
     * This method is used to trigger a deletion of a GitHub repo from the bookmarked repos
     * database.  The operation runs in the background in a coroutine.
     */
    fun removeBookmarkedRepo(repo: GitHubRepo) {
        viewModelScope.launch {
            repository.deleteBookmarkedRepo(repo)
        }
    }

    /**
     * This function triggers a query to the database to fetch a single bookmarked repo based on
     * its name.
     *
     * @return Returns a LiveData object containing the repo matching the specified `name`.  The
     *   LiveData object can be observed to react to the completion of the query.
     */
    fun getBookmarkedRepoByName(name: String) = repository.getBookmarkedRepoByName(name).asLiveData()
}