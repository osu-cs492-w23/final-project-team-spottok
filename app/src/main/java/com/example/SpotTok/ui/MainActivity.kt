package com.example.SpotTok.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.MotionEventCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchwithnavigation.R
import com.example.SpotTok.data.LoadingStatus
import com.google.android.material.progressindicator.CircularProgressIndicator

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val repoListAdapter = LikedSongsAdapter(::onGitHubRepoClick)
    private val viewModel: MainPageViewModel by viewModels()

    private lateinit var appBarConfig: AppBarConfiguration

    private lateinit var searchResultsListRV: RecyclerView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchBoxET: EditText = findViewById(R.id.et_search_box)
        val searchBtn: Button = findViewById(R.id.btn_search)

        searchErrorTV = findViewById(R.id.tv_search_error)
        loadingIndicator = findViewById(R.id.loading_indicator)

        /*
         * Set up RecyclerView.
         */
        searchResultsListRV = findViewById(R.id.rv_search_results)
        searchResultsListRV.layoutManager = LinearLayoutManager(this)
        searchResultsListRV.setHasFixedSize(true)
        searchResultsListRV.adapter = repoListAdapter

        /*
         * Set up the Navigation
         */
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)

        /*
         * Set up an observer on the current search results.  Every time the search results change,
         * send the new search results into the RecyclerView adapter to be displayed.
         */
        viewModel.searchResults.observe(this) { searchResults ->
            repoListAdapter.updateRepoList(searchResults)
        }

        /*
         * Set up an observer on the loading status of the API query.  Display the correct UI
         * elements based on the current loading status.
         */
        viewModel.loadingStatus.observe(this) { loadingStatus ->
            when (loadingStatus) {
                LoadingStatus.LOADING -> {
                    loadingIndicator.visibility = View.VISIBLE
                    searchResultsListRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    searchResultsListRV.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.VISIBLE
                }
                else -> {
                    loadingIndicator.visibility = View.INVISIBLE
                    searchResultsListRV.visibility = View.VISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
            }
        }

        /*
         * Set up an observer on the error message associated with the current API query.  If the
         * error message is not null, display it to the user.
         */
        viewModel.errorMessage.observe(this) { errorMessage->
            if (errorMessage != null) {
                Log.d(TAG, "Error executing search query: $errorMessage")
                searchErrorTV.text = getString(R.string.search_error, errorMessage)
            }
        }

        /*
         * Attach click listener to "search" button to perform repository search with GitHub API
         * using the search query entered by the user.  Also use the values of the appropriate
         * settings to influence the API call.
         */
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        searchBtn.setOnClickListener {
            val query = searchBoxET.text.toString()
            if (!TextUtils.isEmpty(query)) {
                val sort = prefs.getString(getString(R.string.pref_sort_key), null)
                val user = prefs.getString(getString(R.string.pref_user_key), null)
                val firstIssues = prefs.getInt(getString(R.string.pref_first_issues_key), 0)
                val languages = prefs.getStringSet(getString(R.string.pref_language_key), null)
                viewModel.loadSearchResults(query, sort, user, languages, firstIssues)
                searchResultsListRV.scrollToPosition(0)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    /**
     * This method is passed into the RecyclerView adapter to handle clicks on individual items
     * in the list of GitHub repos.  When a repo is clicked, a new activity is launched to view
     * details about that repo.
     */
    private fun onGitHubRepoClick(repo: GitHubRepo) {
        val intent = Intent(this, RepoDetailActivity::class.java)
        intent.putExtra(EXTRA_GITHUB_REPO, repo)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_bookmarks -> {
                val intent = Intent(this, LikedSongsFragment::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {

        val action: Int = MotionEventCompat.getActionMasked(event)

        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "Action was DOWN")
                true
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(TAG, "Action was MOVE")
                true
            }
            MotionEvent.ACTION_UP -> {
                Log.d(TAG, "Action was UP")
                true
            }
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "Action was CANCEL")
                true
            }
            MotionEvent.ACTION_OUTSIDE -> {
                Log.d(TAG, "Movement occurred outside bounds of current screen element")
                true
            }
            else -> super.onTouchEvent(event)
        }
    }
}