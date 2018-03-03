package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*

class   MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manageTracks.setOnClickListener {
            Toast.makeText(this@MainActivity, "Manage tracks clicked", Toast.LENGTH_SHORT).show()
        }

        managePlaylists.setOnClickListener {
            Toast.makeText(this@MainActivity, "Manage playlists clicked", Toast.LENGTH_SHORT).show()
        }

        manageSession.setOnClickListener {
            Toast.makeText(this@MainActivity, "Manage session clicked", Toast.LENGTH_SHORT).show()
        }
    }
}