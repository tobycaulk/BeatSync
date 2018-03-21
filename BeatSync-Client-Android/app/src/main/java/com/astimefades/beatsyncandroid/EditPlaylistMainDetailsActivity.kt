package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.Playlist
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.content_edit_playlist_main_details.*
import org.jetbrains.anko.startActivity

class EditPlaylistMainDetailsActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@EditPlaylistMainDetailsActivity) }
    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_playlist_main_details)

        val playlistId = intent.extras["playlistId"]
        if(playlistId != null) {
            populatePlaylistDetails(playlistId.toString())
            editPlaylistTracks.setOnClickListener { handleEditTracks(playlistId.toString()) }
            editPlaylistSave.setOnClickListener { handleSave(playlistId.toString()) }
        }
    }

    private fun populatePlaylistDetails(playlistId: String) {
        getPlaylistDetails(playlistId, { playlist -> handleSuccessfulPlaylistGet(playlist) })
    }

    private fun handleSuccessfulPlaylistGet(playlist: Playlist) {
        editPlaylistName.setText(playlist.name)
    }

    private fun handleSuccessfulPlaylistSave() {
        Toast.makeText(this@EditPlaylistMainDetailsActivity, "Playlist information saved!", Toast.LENGTH_LONG).show()
        startActivity<ManagePlaylistsActivity>()
    }

    private fun handleEditTracks(playlistId: String) {
        startActivity<EditPlaylistTracksActivity>("playlistId" to playlistId)
    }

    private fun handleSave(playlistId: String) {
        getPlaylistDetails(playlistId, { playlist ->
            playlist.name = editPlaylistName.text.toString()
            persistenceApi.send(
                    Pair(accountConfiguration.getProxyId(), Request(playlist)),
                    persistenceApi::savePlaylist,
                    { _ -> handleSuccessfulPlaylistSave() },
                    this@EditPlaylistMainDetailsActivity
            )
        })
    }

    private fun getPlaylistDetails(playlistId: String, callback: (playlist: Playlist) -> Unit) {
        persistenceApi.send(
                Pair(accountConfiguration.getProxyId(), playlistId),
                persistenceApi::getPlaylist,
                { playlist -> callback(playlist) },
                this@EditPlaylistMainDetailsActivity
        )
    }
}