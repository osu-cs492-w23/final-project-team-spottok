package com.example.SpotTok.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SpotTok.R

class LikedSongsFragment : Fragment(R.layout.liked_songs_fragment) {
    private val viewModel:LikedSongsViewModel by viewModels()
    private val repoListAdapter = GitHubRepoListAdapter(::onGitHubRepoClick)
    private lateinit var bookmarkedReposRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liked_songs_fragment)

        /*
         * Set up RecyclerView
         */
        bookmarkedReposRV = findViewById(R.id.rv_bookmarked_repos)
        bookmarkedReposRV.layoutManager = LinearLayoutManager(this)
        bookmarkedReposRV.setHasFixedSize(true)
        bookmarkedReposRV.adapter = repoListAdapter

        viewModel.bookmarkedRepos.observe(this) {
            repoListAdapter.updateRepoList(it)
        }
    }

    /**
     * This method is passed into the RecyclerView adapter to handle clicks on individual items
     * in the list of GitHub repos.  When a repo is clicked, a new activity is launched to view
     * details about that repo.
     */
    private fun onGitHubRepoClick(repo: GitHubRepo) {
        val intent = Intent(this, RepoDetailActivity::class.java).apply {
            putExtra(EXTRA_GITHUB_REPO, repo)
        }
        startActivity(intent)
    }
}