package com.astimefades.beatsyncservice.service

import com.astimefades.beatsyncservice.model.Playlist
import com.astimefades.beatsyncservice.model.db.AccountRepository
import com.astimefades.beatsyncservice.model.error.BeatSyncError
import com.astimefades.beatsyncservice.util.Util
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PlaylistService(@Autowired accountRepository: AccountRepository): BaseAccountService(accountRepository) {

    fun create(id: String, playlist: Playlist): Playlist {
        var account = getAccountByProxyId(id)
        if(account != null) {
            playlist.id = ObjectId().toString()
            account.addPlaylist(playlist)

            updateAccount(account)

            return playlist
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun get(id: String, playlistId: String): Playlist? {
        var account = getAccountByProxyId(id)
        if(account != null) {
            val playlist = Util.getModelFromListById(account.playlists, playlistId)
            playlist.tracks.forEach { trackId ->
                playlist.fqTracks.add(account.tracks.single { accountTrack -> accountTrack.id == trackId })
            }

            return playlist
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun getAll(proxyId: String): List<Playlist>? {
        var account = getAccountByProxyId(proxyId)
        if(account != null) {
            return account.playlists
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun remove(proxyId: String, playlistId: String): Boolean {
        var account = getAccountByProxyId(proxyId)
        if(account != null) {
            var playlistToRemove = Util.removeModelFromListById(account.playlists, playlistId)
            updateAccount(account)

            return !getAccountByProxyId(proxyId).playlists.contains(playlistToRemove)
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun update(id: String, playlist: Playlist): Playlist? {
        var account = getAccountByProxyId(id)
        if(account != null) {
            Util.removeModelFromListById(account.playlists, playlist.id)
            account.playlists.add(playlist)

            updateAccount(account)

            return get(id, playlist.id)
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }
}