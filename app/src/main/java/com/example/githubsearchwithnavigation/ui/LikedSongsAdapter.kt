package com.example.githubsearchwithnavigation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubsearchwithnavigation.data.SpotifyEntity
import com.example.githubsearchwithnavigation.R

class LikedSongsAdapter(
    private val onLikedSongClick: (SpotifyEntity) -> Unit
): RecyclerView.Adapter<LikedSongsAdapter.LikedSongsViewHolder>() {
    private var likedSongList = listOf<SpotifyEntity>()


    // update the list of songs in the bookmarked/liked songs the user likes
    fun updateLikedSongList(newLikedSongList: List<SpotifyEntity>?) {
        likedSongList = newLikedSongList ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = likedSongList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedSongsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_list_item, parent, false)
        return LikedSongsViewHolder(itemView, onLikedSongClick)
    }

    override fun onBindViewHolder(holder: LikedSongsViewHolder, position: Int) {
        //val view = LayoutInflater.from(parent.context)
        //    .inflate(R.layout.main_page_fragment, parent, false)
        //return SongAdapter.ViewHolder(view, onClick)
        holder.bind(likedSongList[position])
    }

    class LikedSongsViewHolder(
        itemView: View,
        private val onClick: (SpotifyEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        // use itemView.findViewById(R.id.tv_song_name)
        // create another one with itemView.findViewById(R.id.tv_artist_name)

        private var songNameTV: TextView = itemView.findViewById(R.id.tv_liked_song_title)
        private var artistNameTV: TextView = itemView.findViewById(R.id.tv_liked_song_artist)

        private lateinit var currentLikedSong: SpotifyEntity

        // Snackbar popup here to show that the user has liked the song (in the fragment class)
        init {
           itemView.setOnClickListener {
                currentLikedSong.let(onClick)
           }
        }
        fun bind (spotifyEntity: SpotifyEntity) {
            currentLikedSong = spotifyEntity
            songNameTV.text = spotifyEntity.songName
            artistNameTV.text = spotifyEntity.artistName
        }

    }







}