package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.content_add_track.*
import org.jetbrains.anko.startActivity

class AddTrackActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@AddTrackActivity) }
    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_track)

        addTrackCreate.setOnClickListener{ handleCreate() }
    }

    private fun handleCreate() {
        var track = Track()
        track.name = addTrackName.text.toString()
        track.bpm = addTrackBpm.text.toString().toInt()
        track.length = addTrackLength.text.toString().toInt()

        persistenceApi.send(
                Pair(accountConfiguration.getProxyId(), Request(track)),
                persistenceApi::createTrack,
                { track -> handleSuccessfulTrackCreate(track) },
                this@AddTrackActivity
        )
    }

    private fun handleSuccessfulTrackCreate(track: Track) {
        Toast.makeText(this@AddTrackActivity, "Track created!", Toast.LENGTH_LONG).show()
        startActivity<ManageTracksActivity>()
    }
}
