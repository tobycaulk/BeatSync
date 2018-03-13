package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.content_edit_track.*
import org.jetbrains.anko.startActivity

class EditTrackActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@EditTrackActivity) }
    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_track)

        var trackId = intent.extras["trackId"]
        if(trackId != null) {
            populateTrackDetails(trackId.toString())
        }
    }

    private fun populateTrackDetails(trackId: String) {
        persistenceApi.send(
                Request(Pair(accountConfiguration.getProxyId(), trackId)),
                persistenceApi::getTrack,
                { track -> handleSuccessfulTrackGet(track) },
                { errorDescription, _ -> handleTrackGetError(errorDescription) },
                this@EditTrackActivity
        )
    }

    private fun handleSuccessfulTrackGet(track: Track) {
        editTrackName.setText(track.name)
        editTrackBpm.setText(track.bpm.toString())
        editTrackLength.setText(track.length.toString())
    }

    private fun handleTrackGetError(errorDescription: String) {
        Toast.makeText(this@EditTrackActivity, "Error while retrieving track information", Toast.LENGTH_LONG).show()
        startActivity<ManageTracksActivity>()
    }
}