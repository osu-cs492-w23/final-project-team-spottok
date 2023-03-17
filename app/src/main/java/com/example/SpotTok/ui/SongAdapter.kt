package com.example.SpotTok.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SpotTok.data.SpotifyPlaylistResults
import com.example.SpotTok.data.TrackInfo
import com.example.SpotTok.data.Tracks
import com.example.githubsearchwithnavigation.R

/**
 * This is the constructor for a RecyclerView adapter for five-day forecast data.
 *
 * @param onClick This should be a function for handling a click on an individual forecast
 *   in the list of forecast items managed by this adapter.  When the item is clicked, a
 *   representation of that item is passed into this function as a `ForecastPeriod` object.
 */
class SongAdapter(private val onClick: (Tracks) -> Unit)
    : RecyclerView.Adapter<SongAdapter.ViewHolder>() {
    var allTracks: List<Tracks> = listOf()

    // used to update new playlist items ON MAIN PAGE
    fun updatePlaylist(spotifyPlaylistResults: SpotifyPlaylistResults) {
        allTracks = spotifyPlaylistResults.items ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = this.allTracks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the layout for the main page fragment here
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_page_fragment, parent, false)
        return ViewHolder(view, onClick)
    }

// ADJUST THIS TO FIT WITH PROJECT
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(this.allTracks[position])
//    }


    // NEW ITEM VIEW. USE DATA FROM DATA FOLDERS TO LOAD DATA FOR MAIN PAGE,
    // EX. SONG NAME, ARTIST, SPOTIFY LINK, URI TO TRACK/SONG
    class ViewHolder(itemView: View, val onClick: (Tracks) -> Unit)
        : RecyclerView.ViewHolder(itemView) {

        // recycler view for hte main page requirements
        // Probably : Song Name, Artist
//        private val dateTV: TextView = itemView.findViewById(R.id.tv_date)
//        private val timeTV: TextView = itemView.findViewById(R.id.tv_time)
//        private val highTempTV: TextView = itemView.findViewById(R.id.tv_high_temp)
//        private val lowTempTV: TextView = itemView.findViewById(R.id.tv_low_temp)
//        private val iconIV: ImageView = itemView.findViewById(R.id.iv_forecast_icon)
//        private val popTV: TextView = itemView.findViewById(R.id.tv_pop)

        private val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(itemView.context)




        // CAN HAVE ON CLICK LISTENER IF YOU WANT, BUT WE ARE NOT ALLOWING USERS TO CLICK I THINK
        // USERS CAN PLAY/PAUSE AND LIKE SONGS. IF THEY LIKE SONG
        // ADD TRACK.NAME AND TRACK.NAME.ARTIST TO THE DATABASE

//        init {
//            itemView.setOnClickListener {
//                currentForecastPeriod.let(onClick)
//            }
//        }

//        fun bind(forecastPeriod: ForecastPeriod, forecastCity: ForecastCity?) {
//            currentForecastPeriod = forecastPeriod
//
//            val ctx = itemView.context
//            val date = openWeatherEpochToDate(forecastPeriod.epoch, forecastCity?.tzOffsetSec ?: 0)
//
//            /*
//             * Figure out the correct temperature and wind units to display for the current
//             * setting of the units preference.
//             */
//            val units = sharedPrefs.getString(ctx.getString(R.string.pref_units_key), null)
//            val tempUnitsDisplay = getTempUnitsDisplay(units, ctx)
//
//            dateTV.text = ctx.getString(R.string.forecast_date, date)
//            timeTV.text = ctx.getString(R.string.forecast_time, date)
//            highTempTV.text = ctx.getString(
//                R.string.forecast_temp,
//                forecastPeriod.highTemp,
//                tempUnitsDisplay
//            )
//            lowTempTV.text = ctx.getString(
//                R.string.forecast_temp,
//                forecastPeriod.lowTemp,
//                tempUnitsDisplay
//            )
//            popTV.text = ctx.getString(R.string.forecast_pop, forecastPeriod.pop)

        /*
             * Load forecast icon into ImageView using Glide: https://bumptech.github.io/glide/
             */
//            Glide.with(ctx)
//                .load(forecastPeriod.iconUrl)
//                .into(iconIV)
//        }
    }
}