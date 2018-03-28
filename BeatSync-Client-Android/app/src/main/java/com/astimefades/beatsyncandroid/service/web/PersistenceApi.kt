package com.astimefades.beatsyncandroid.service.web

import com.astimefades.beatsyncandroid.model.Playlist
import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.service.AccountService

/**
 * Created by tobycaulk on 3/4/18.
 */
class PersistenceApi: ApiCaller<AccountService>(AccountService::class.java) {

    fun loginAccount(request: Request<LoginAccountRequest>) = webService.loginAccount(request)

    fun createAccount(request: Request<CreateAccountRequest>) = webService.createAccount(request)

    fun checkAccountLogin(request: Request<String>) = webService.checkAccountLogin(request)

    fun getAllTracks(request: String) = webService.getAllTracks(request)

    fun getTrack(request: Pair<String, String>) = webService.getTrack(request.first, request.second)

    fun saveTrack(request: Pair<String, Request<Track>>) = webService.saveTrack(request.first, request.second)

    fun createTrack(request: Pair<String, Request<Track>>) = webService.createTrack(request.first, request.second)

    fun getAllPlaylists(request: String) = webService.getAllPlaylists(request)

    fun getPlaylist(request: Pair<String, String>) = webService.getPlaylist(request.first, request.second)

    fun savePlaylist(request: Pair<String, Request<Playlist>>) = webService.savePlaylist(request.first, request.second)

    fun createPlaylist(request: Pair<String, Request<Playlist>>) = webService.createPlaylist(request.first, request.second)
}