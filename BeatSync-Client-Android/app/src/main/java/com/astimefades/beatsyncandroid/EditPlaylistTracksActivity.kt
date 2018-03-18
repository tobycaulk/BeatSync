package com.astimefades.beatsyncandroid

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.astimefades.beatsyncandroid.model.Playlist
import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.content_edit_playlist_tracks.*
import kotlinx.android.synthetic.main.simple_card.view.*

class EditPlaylistTracksActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@EditPlaylistTracksActivity) }
    private val persistenceApi = PersistenceApi()

    private var trackState = hashMapOf<Track, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_playlist_tracks)

        val playlistId = intent.extras["playlistId"]
        if(playlistId != null) {
            populatePlaylistDetails(playlistId.toString())
        }
    }

    private fun populatePlaylistDetails(playlistId: String) {
        persistenceApi.send(
                Pair(accountConfiguration.getProxyId(), playlistId),
                persistenceApi::getPlaylist,
                { playlist -> handleSuccessfulPlaylistGet(playlist) },
                this@EditPlaylistTracksActivity
        )
    }

    private fun handleSuccessfulPlaylistGet(playlist: Playlist) {
        editPlaylistTracksName.text = playlist.name
        populatePlaylistTracks(playlist)
    }

    private fun populatePlaylistTracks(playlist: Playlist) {
        persistenceApi.send(
                accountConfiguration.getProxyId(),
                persistenceApi::getAllTracks,
                { tracks ->
                    tracks.forEach { track ->
                        var selected = playlist.fqTracks.any { fqTrack -> fqTrack.id == track.id }
                        trackState.put(track, selected)
                    }

                    playlistTrackList.adapter = EditPlaylistTrackAdapter(this@EditPlaylistTracksActivity, trackState.keys.toList())
                    playlistTrackList.setOnItemClickListener { _, view, position, _ ->
                        var textView: TextView? = view as? TextView
                        if(textView != null) {
                            val track = trackState.keys.single { t -> textView.text == t.name }
                            trackState.set(track, !trackState.get(track)!!)
                            var selected = trackState.get(track)!!
                            if(selected) {
                                view.background = ContextCompat.getDrawable(this@EditPlaylistTracksActivity, R.color.colorAccent)
                            } else {
                                view.background = ContextCompat.getDrawable(this@EditPlaylistTracksActivity, R.color.white)
                            }
                        }
                    }
                },
                this@EditPlaylistTracksActivity
        )
    }

    inner class EditPlaylistTrackAdapter(context: Context?, tracks: List<Track>?) : ArrayAdapter<Track>(context, R.layout.simple_card, tracks) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var returnedView: View? = convertView
            val track = getItem(position)
            if(returnedView == null) {
                returnedView = LayoutInflater.from(context).inflate(R.layout.simple_card, parent,false)
            }

            returnedView!!.simpleCard.text = track.name
            var trackSelected = trackState.get(track)
            if(trackSelected != null) {
                if(trackSelected) {
                    returnedView.background = ContextCompat.getDrawable(this@EditPlaylistTracksActivity, R.color.colorAccent)
                } else {
                    returnedView.background = ContextCompat.getDrawable(this@EditPlaylistTracksActivity, R.color.white)
                }
            }

            return returnedView
        }
    }
}