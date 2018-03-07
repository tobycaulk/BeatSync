package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.content_manage_tracks.*

class ManageTracksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_tracks)

        val listAdapter = ArrayAdapter<String>(this@ManageTracksActivity, R.layout.simple_card, arrayListOf( "1", "2", "3", "5", "6" ))
        trackList.setAdapter(listAdapter)
    }
}