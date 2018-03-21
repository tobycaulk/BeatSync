package com.astimefades.beatsyncandroid

import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ArrayAdapter
import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.activity_manage_tracks.*
import kotlinx.android.synthetic.main.content_manage_tracks.*
import org.jetbrains.anko.startActivity

class ManageTracksActivity : BottomNavigationActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@ManageTracksActivity) }
    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tracks)

        bottomNavigation.setOnNavigationItemSelectedListener { item -> handleNavigationItemClicked(item) }

        addTrackButton.setOnClickListener {
            startActivity<AddTrackActivity>()
        }

        populateTracks()
    }

    private fun populateTracks() {
        persistenceApi.send(
                accountConfiguration.getString(AccountConfiguration.ACCOUNT_PROXY_ID_PROP),
                persistenceApi::getAllTracks,
                { tracks: List<Track> ->
                    var trackNames: List<String> = tracks.map { it.name }
                    trackList.adapter = ArrayAdapter<String>(this@ManageTracksActivity, R.layout.simple_card, trackNames)
                    trackList.setOnItemClickListener { _, _, position, _ ->
                        val track = tracks[position]
                        startActivity<EditTrackActivity>("trackId" to track.id)
                    }
                },
                this@ManageTracksActivity)
    }
}