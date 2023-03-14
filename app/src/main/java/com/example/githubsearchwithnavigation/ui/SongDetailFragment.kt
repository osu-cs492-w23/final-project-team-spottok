package com.example.githubsearchwithnavigation.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.githubsearchwithnavigation.R
import com.google.android.material.snackbar.Snackbar

const val EXTRA_GITHUB_REPO = "GITHUB_REPO"

class RepoDetailActivity : Fragment(R.layout.song_detail_fragment) {
//    private var repo: GitHubRepo? = null
    private var isBookmarked = false

    private val viewModel: LikedSongsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
         * If an intent was used to launch this activity and it contains information about a
         * GitHub repo, use that information to populate the UI.
         */
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            repo = intent.getSerializableExtra(EXTRA_GITHUB_REPO) as GitHubRepo

            findViewById<TextView>(R.id.tv_repo_name).text = repo!!.name
            findViewById<TextView>(R.id.tv_repo_stars).text = repo!!.stars.toString()
            findViewById<TextView>(R.id.tv_repo_description).text = repo!!.description
        }
    }

    /**
     * This method adds a custom menu to the action bar for this activity.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_repo_detail, menu)

        /*
         * Here, where we know we can access the bookmark action in the action bar, set up an
         * observer on the status of the current GitHub repo in the bookmarked repos database.
         * If the repo is not in the database (i.e. a query for the repo returns null), then
         * set the bookmark icon in the action bar to reflect that the repo is not bookmarked.
         * If the repo is in the database, update the bookmark icon to reflect that the repo
         * is bookmarked.
         */
        val bookmarkItem = menu?.findItem(R.id.action_bookmark)
        viewModel.getBookmarkedRepoByName(repo!!.name).observe(this) { bookmarkedRepo ->
            when (bookmarkedRepo) {
                null -> {
                    isBookmarked = false
                    bookmarkItem?.isChecked = false
                    bookmarkItem?.icon = AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_action_bookmark_off
                    )
                }
                else -> {
                    isBookmarked = true
                    bookmarkItem?.isChecked = true
                    bookmarkItem?.icon = AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_action_bookmark_on
                    )
                }
            }
        }
        return true
    }

    /**
     * This method handles clicks on actions in the action bar menu for this activity.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_view_on_web -> {
                viewRepoOnWeb()
                true
            }
            R.id.action_share -> {
                shareRepo()
                true
            }
            R.id.action_bookmark -> {
                toggleRepoBookmark(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method toggles bookmark state of the current GitHub repo.  It either adds or removes
     * the repo from the bookmarked repos database (depending on whether the repo is/is not
     * already in the database), and it maintains the state of the bookmark icon in the action bar
     * to reflect whether the repo is bookmarked or not.
     */
    private fun toggleRepoBookmark(menuItem: MenuItem) {
        if (repo != null) {
            when (isBookmarked) {
                false -> viewModel.addBookmarkedRepo(repo!!)
                true ->  viewModel.removeBookmarkedRepo(repo!!)
            }
        }
    }

    /**
     * This method constructs an implicit intent to open the device's browser to view the current
     * repo on github.com using the URL associated with the repo.
     */
    private fun viewRepoOnWeb() {
        if (repo != null) {
            val intent = Uri.parse(repo!!.url).let {
                Intent(Intent.ACTION_VIEW, it)
            }

            /*
             * Make sure the device has an appropriate app to handle the implicit intent.  If it
             * doesn't, display an error message in a Snackbar.
             */
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(
                    findViewById(R.id.coordinator_layout),
                    R.string.action_view_on_web_error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * This method opens the Android Sharesheet to allow the user to share information about the
     * current GitHub repo.
     */
    private fun shareRepo() {
        if (repo != null) {
            val text = getString(R.string.share_text, repo!!.name, repo!!.url)
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, null))
        }
    }
}