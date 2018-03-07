package com.astimefades.beatsyncservice.service

import com.astimefades.beatsyncservice.model.Track
import com.astimefades.beatsyncservice.model.db.AccountRepository
import com.astimefades.beatsyncservice.model.error.BeatSyncError
import com.astimefades.beatsyncservice.util.Util
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TrackService(@Autowired accountRepository: AccountRepository): BaseAccountService(accountRepository) {

    fun create(id: String, track: Track): Track {
        var account = getAccountByProxyId(id)
        if(account != null) {
            track.id = ObjectId().toString()
            account.addTrack(track)

            updateAccount(account)

            return track
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun get(id: String, trackId: String): Track? {
        var account = getAccountByProxyId(id)
        if(account != null) {
            return Util.getModelFromListById(account.tracks, trackId)
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun getAll(proxyId: String): List<Track>? {
        var account = getAccountByProxyId(proxyId)
        if(account != null) {
            return account.tracks
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun remove(proxyId: String, trackId: String): Boolean {
        var account = getAccountByProxyId(proxyId)
        if(account != null) {
            var trackToRemove = Util.removeModelFromListById(account.tracks, trackId)
            updateAccount(account)

            return !getAccountByProxyId(proxyId).tracks.contains(trackToRemove)
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }

    fun update(id: String, track: Track): Track? {
        var account = getAccountByProxyId(id)
        if(account != null) {
            Util.removeModelFromListById(account.tracks, track.id)
            account.tracks.add(track)

            updateAccount(account)

            return get(id, track.id)
        } else {
            throw BeatSyncError.getException(BeatSyncError.INVALID_PROXY_ID)
        }
    }
}