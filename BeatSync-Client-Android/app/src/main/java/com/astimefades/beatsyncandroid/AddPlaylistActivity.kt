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
import kotlinx.android.synthetic.main.activity_add_playlist.*
import kotlinx.android.synthetic.main.content_add_playlist.*
import kotlinx.android.synthetic.main.simple_card.view.*
import org.jetbrains.anko.startActivity

class AddPlaylistActivity : BottomNavigationActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@AddPlaylistActivity) }
    private val persistenceApi = PersistenceApi()

    private var trackState = hashMapOf<Track, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_playlist)

        bottomNavigation.setOnNavigationItemSelectedListener { item -> handleNavigationItemClicked(item) }

        addPlaylistSave.setOnClickListener { handleAddPlaylist() }

        populateTracks()
    }

    private fun handleAddPlaylist() {
        val selectedTracks: List<String> = trackState
                .filterValues { selected -> selected }
                .keys.map{ track ->  track.id }

        val playlist = Playlist()
        playlist.name = addPlaylistName.text.toString()
        playlist.tracks = selectedTracks

        persistenceApi.send(
            Pair(accountConfiguration.getProxyId(), Request(playlist)),
            persistenceApi::createPlaylist,
            { _ ->
                Toast.makeText(this@AddPlaylistActivity, "Playlist information saved!", Toast.LENGTH_LONG)
                startActivity<ManagePlaylistsActivity>()
            },
            this@AddPlaylistActivity
        )
    }

    private fun populateTracks() {
        persistenceApi.send(
                accountConfiguration.getProxyId(),
                persistenceApi::getAllTracks,
                { tracks ->
                    tracks.forEach{ track -> trackState.put(track, false) }

                    playlistTrackList.adapter = AddPlaylistTrackAdapter(this@AddPlaylistActivity, tracks)
                    playlistTrackList.setOnItemClickListener {_, view, _, _ ->
                        var textView: TextView? = view as? TextView
                        if(textView != null) {
                            val track = trackState.keys.single { t -> textView.text == t.name }
                            trackState[track] = !trackState[track]!!
                            var selected = trackState[track]!!
                            if(selected) {
                                view.background = ContextCompat.getDrawable(this@AddPlaylistActivity, R.color.colorAccent)
                            } else {
                                view.background = ContextCompat.getDrawable(this@AddPlaylistActivity, R.color.white)
                            }
                        }
                    }
                },
                this@AddPlaylistActivity
        )
    }

    inner class AddPlaylistTrackAdapter(context: Context?, tracks: List<Track>?): ArrayAdapter<Track>(context, R.layout.simple_card, tracks) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var returnedView: View? = convertView
            if(returnedView == null) {
                returnedView = LayoutInflater.from(context).inflate(R.layout.simple_card, parent, false)
            }

            var track = getItem(position)
            returnedView!!.simpleCard.text = track.name
            var trackSelected = trackState[track]
            if(trackSelected != null) {
                if(trackSelected) {
                    returnedView.background = ContextCompat.getDrawable(this@AddPlaylistActivity, R.color.colorAccent)
                } else {
                    returnedView.background = ContextCompat.getDrawable(this@AddPlaylistActivity, R.color.white)
                }
            }

            return returnedView
        }
    }
}
