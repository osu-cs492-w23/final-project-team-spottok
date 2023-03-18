package com.example.SpotTok.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.SpotTok.data.SpotifyEntity
import com.example.SpotTok.data.Tracks
import com.example.githubsearchwithnavigation.R
import kotlin.reflect.KFunction1

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

    class LikedSongsViewHolder(
        itemView: View,
        private val onClick: (SpotifyEntity) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        // use itemView.findViewById(R.id.tv_song_name)
        // create another one with itemView.findViewById(R.id.tv_artist_name)

//        private val songNameTV: TextView = "SONG NAME HERE NELSON"
//        private val artistNameTV: TextView = "ARTIST NAME HERE NELSON"

        private var currentLikedSong: SpotifyEntity? = null

        // Snackbar popup here to show that the user has liked the song (in the fragment class)
        init {
           itemView.setOnClickListener {
                currentLikedSong?.let(onClick)
           }
        }


    }







}