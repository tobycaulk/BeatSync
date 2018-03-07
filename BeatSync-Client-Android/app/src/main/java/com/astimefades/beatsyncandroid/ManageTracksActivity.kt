package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.content_manage_tracks.*

class ManageTracksActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@ManageTracksActivity) }
    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tracks)

        persistenceApi.send(
                Request(accountConfiguration.getString(AccountConfiguration.ACCOUNT_PROXY_ID_PROP)),
                persistenceApi::getAllTracks,
                { tracks: List<Track> ->
                    trackList.adapter = ArrayAdapter<Track>(this@ManageTracksActivity, R.layout.simple_card, tracks)
                    Log.i("ManageTracksActivity", tracks.size.toString())
                },
                this@ManageTracksActivity)
    }
}