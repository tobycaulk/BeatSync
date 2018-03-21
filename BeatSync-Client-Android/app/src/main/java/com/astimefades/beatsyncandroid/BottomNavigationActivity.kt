package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.astimefades.beatsyncandroid.R.id.*
import org.jetbrains.anko.startActivity

/**
 * Created by tobycaulk on 3/21/18.
 */
open abstract class BottomNavigationActivity: AppCompatActivity() {

    protected fun handleNavigationItemClicked(item: MenuItem): Boolean {
        when(item.itemId) {
            action_tracks -> startActivity<ManageTracksActivity>()
            action_playlists -> startActivity<ManagePlaylistsActivity>()
            action_session -> Toast.makeText(this@BottomNavigationActivity, "Manage session clicked", Toast.LENGTH_SHORT).show()
        }

        return true
    }
}