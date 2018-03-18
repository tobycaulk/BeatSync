package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.content_manage_playlists.*
import org.jetbrains.anko.startActivity

class ManagePlaylistsActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@ManagePlaylistsActivity) }
    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_playlists)

        populatePlaylists()
    }

    private fun populatePlaylists() {
        persistenceApi.send(
                accountConfiguration.getProxyId(),
                persistenceApi::getAllPlaylists,
                { allPlaylists ->
                    var playlistNames: List<String> = allPlaylists.map { it.name }
                    playlists.adapter = ArrayAdapter<String>(this@ManagePlaylistsActivity, R.layout.simple_card, playlistNames)
                    playlists.setOnItemClickListener {_, _, position, _ ->
                        val playlist = allPlaylists[position]
                        startActivity<EditPlaylistMainDetailsActivity>("playlistId" to playlist.id)
                    }
                },
                this@ManagePlaylistsActivity
        )
    }
}
