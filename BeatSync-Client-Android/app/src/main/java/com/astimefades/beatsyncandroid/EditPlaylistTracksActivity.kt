package com.astimefades.beatsyncandroid

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.Playlist
import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.activity_edit_playlist_tracks.*
import kotlinx.android.synthetic.main.content_edit_playlist_tracks.*
import kotlinx.android.synthetic.main.simple_card.view.*
import org.jetbrains.anko.startActivity

class EditPlaylistTracksActivity : BottomNavigationActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@EditPlaylistTracksActivity) }
    private val persistenceApi = PersistenceApi()

    private var trackState = hashMapOf<Track, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_playlist_tracks)

        bottomNavigation.setOnNavigationItemSelectedListener { item -> handleNavigationItemClicked(item) }

        val playlistId = intent.extras["playlistId"]
        if(playlistId != null) {
            populatePlaylistDetails(playlistId.toString())
            editPlaylistTracksSave.setOnClickListener { handleSave(playlistId.toString()) }
        }
    }

    private fun handleSave(playlistId: String) {
        val selectedTracks: List<String> = trackState
                .filterValues { selected -> selected }
                .keys.map{ track ->  track.id }
        getPlaylistDetails(playlistId, { playlist ->
            playlist.tracks = selectedTracks
            persistenceApi.send(
                    Pair(accountConfiguration.getProxyId(), Request(playlist)),
                    persistenceApi::savePlaylist,
                    { _ ->
                        Toast.makeText(this@EditPlaylistTracksActivity, "Playlist information saved!", Toast.LENGTH_LONG)
                        startActivity<ManagePlaylistsActivity>()
                    },
                    this@EditPlaylistTracksActivity
            )
        })
    }

    private fun populatePlaylistDetails(playlistId: String) {
        getPlaylistDetails(playlistId, { playlist -> handleSuccessfulPlaylistGet(playlist) })
    }

    private fun getPlaylistDetails(playlistId: String, callback: (playlist: Playlist) -> Unit) {
        persistenceApi.send(
                Pair(accountConfiguration.getProxyId(), playlistId),
                persistenceApi::getPlaylist,
                { playlist -> callback(playlist) },
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
                    playlistTrackList.setOnItemClickListener { _, view, _, _ ->
                        var textView: TextView? = view as? TextView
                        if(textView != null) {
                            val track = trackState.keys.single { t -> textView.text == t.name }
                            trackState[track] = !trackState[track]!!
                            var selected = trackState[track]!!
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

    inner class EditPlaylistTrackAdapter(context: Context?, tracks: List<Track>?): ArrayAdapter<Track>(context, R.layout.simple_card, tracks) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var returnedView: View? = convertView
            if(returnedView == null) {
                returnedView = LayoutInflater.from(context).inflate(R.layout.simple_card, parent,false)
            }

            val track = getItem(position)
            returnedView!!.simpleCard.text = track.name
            var trackSelected = trackState[track]
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