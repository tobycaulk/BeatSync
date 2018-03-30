package com.astimefades.beatsyncservice.controller;

import com.astimefades.beatsyncservice.model.Account;
import com.astimefades.beatsyncservice.model.Playlist;
import com.astimefades.beatsyncservice.model.Session;
import com.astimefades.beatsyncservice.model.Track;
import com.astimefades.beatsyncservice.model.request.CreateAccountRequest;
import com.astimefades.beatsyncservice.model.request.LoginAccountRequest;
import com.astimefades.beatsyncservice.model.request.Request;
import com.astimefades.beatsyncservice.model.response.Response;
import com.astimefades.beatsyncservice.service.AccountService;
import com.astimefades.beatsyncservice.service.PlaylistService;
import com.astimefades.beatsyncservice.service.SessionService;
import com.astimefades.beatsyncservice.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController extends Controller {

    private AccountService accountService;
    private TrackService trackService;
    private PlaylistService playlistService;
    private SessionService sessionService;

    @Autowired
    public AccountController(AccountService accountService,
                             TrackService trackService,
                             PlaylistService playlistService,
                             SessionService sessionService) {
        this.accountService = accountService;
        this.trackService = trackService;
        this.playlistService = playlistService;
        this.sessionService = sessionService;
    }

    @PostMapping("/login/check")
    public Response<Boolean> checkAccountLogin(@RequestBody Request<String> request) {
        return process(req -> accountService.checkLogin(req), request);
    }

    @PostMapping("/login")
    public Response<String> loginAccount(@RequestBody Request<LoginAccountRequest> request) {
        return process(req -> accountService.login(req), request);
    }

    @PostMapping("/")
    public Response<String> createAccount(@RequestBody Request<CreateAccountRequest> request) {
        return process(req -> accountService.create(req), request);
    }

    @PostMapping("/logout/{id}")
    public Response<Boolean> logout(@PathVariable("id") String id) {
        return processNoRequest(req -> accountService.logout(req), id);
    }

    @GetMapping("/{id}")
    public Response<Account> getAccount(@PathVariable("id") String id) {
        return processNoRequest(req -> accountService.getAccount(req), id);
    }

    @DeleteMapping("/{id}")
    public Response<Boolean> removeAccount(@PathVariable("id") String id) {
        return processNoRequest(req -> accountService.removeAccount(req), id);
    }

    @GetMapping("/{id}/track/all")
    public Response<List<Track>> getAllTracks(@PathVariable("id") String id) {
        return processNoRequest(req -> trackService.getAll(req), id);
    }

    @GetMapping("/{proxyId}/track/{trackId}")
    public Response<Track> getTrack(@PathVariable("proxyId") String proxyId, @PathVariable("trackId") String trackId) {
        return processNoRequest(req -> trackService.get(proxyId, trackId), null);
    }

    @PostMapping("/{id}/track")
    public Response<Track> createTrack(@PathVariable("id") String id, @RequestBody Request<Track> request) {
        return process(req -> trackService.create(id, req), request);
    }

    @PatchMapping("/{id}/track")
    public Response<Track> updateTrack(@PathVariable("id") String id, @RequestBody Request<Track> request) {
        return process(req -> trackService.update(id, req), request);
    }

    @DeleteMapping("/{proxyId}/track/{trackId}")
    public Response<Boolean> deleteTrack(@PathVariable("proxyId") String proxyId, @PathVariable("trackId") String trackId) {
        return processNoRequest(req -> trackService.remove(proxyId, trackId), null);
    }

    @PostMapping("/{id}/playlist")
    public Response<Playlist> createPlaylist(@PathVariable("id") String id, @RequestBody Request<Playlist> request) {
        return process(req -> playlistService.create(id, req), request);
    }

    @GetMapping("/{id}/playlist/all")
    public Response<List<Playlist>> getAllPlaylists(@PathVariable("id") String proxyId) {
        return processNoRequest(req -> playlistService.getAll(req), proxyId);
    }

    @GetMapping("/{proxyId}/playlist/{playlistId}")
    public Response<Playlist> getPlaylist(@PathVariable("proxyId") String proxyId, @PathVariable("playlistId") String playlistId) {
        return processNoRequest(req -> playlistService.get(proxyId, playlistId), null);
    }

    @PatchMapping("/{id}/playlist")
    public Response<Playlist> updatePlaylist(@PathVariable("id") String id, @RequestBody Request<Playlist> request) {
        return process(req -> playlistService.update(id, req), request);
    }

    @DeleteMapping("/{proxyId}/playlist/{playlistId}")
    public Response<Boolean> deletePlaylist(@PathVariable("proxyId") String proxyId, @PathVariable("playlistId") String playlistId) {
        return processNoRequest(req -> playlistService.remove(proxyId, playlistId), null);
    }

    @PostMapping("/{id}/session/")
    public Response<Session> createSession(@PathVariable("id") String id, @RequestBody Request<Session> request) {
        return process(req -> sessionService.create(id, req), request);
    }
}