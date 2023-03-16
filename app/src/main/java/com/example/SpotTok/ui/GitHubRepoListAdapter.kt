package com.example.SpotTok.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.SpotTok.data.TrackInfo
import com.example.githubsearchwithnavigation.R


class GitHubRepoListAdapter(
    private val onGitHubRepoClick: (TrackInfo) -> Unit
) : RecyclerView.Adapter<GitHubRepoListAdapter.GitHubRepoViewHolder>() {
    private var gitHubRepoList = listOf<TrackInfo>()


    fun updateRepoList(newRepoList: List<TrackInfo>?) {
        gitHubRepoList = newRepoList ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = gitHubRepoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitHubRepoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.github_repo_list_item, parent, false)
        return GitHubRepoViewHolder(itemView, onGitHubRepoClick)
    }

    override fun onBindViewHolder(holder: GitHubRepoViewHolder, position: Int) {
        holder.bind(gitHubRepoList[position])
    }

    class GitHubRepoViewHolder(
        itemView: View,
        private val onClick: (TrackInfo) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private var currentTrackInfo: TrackInfo? = null

        /*
         * Set up a click listener on this individual ViewHolder.  Call the provided onClick
         * function, passing the repo represented by this ViewHolder as an argument.
         */
        init {
            itemView.setOnClickListener {
                currentTrackInfo?.let(onClick)
            }

        }

        fun bind(trackInfo: TrackInfo) {
            currentTrackInfo = trackInfo
            nameTV.text = trackInfo.songName
        }
    }
}