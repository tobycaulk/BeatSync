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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController extends Controller {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login/check")
    public Response<Boolean> checkAccountLogin(@RequestBody Request<String> request) {
        return process(req -> accountService.checkAccountLogin(req), request);
    }

    @PostMapping("/login")
    public Response<String> loginAccount(@RequestBody Request<LoginAccountRequest> request) {
        return process(req -> accountService.loginAccount(req), request);
    }

    @PostMapping("/")
    public Response<String> createAccount(@RequestBody Request<CreateAccountRequest> request) {
        return process(req -> accountService.createAccount(req), request);
    }

    @GetMapping("/{id}")
    public Response<Account> getAccount(@PathVariable("id") String id) {
        return processNoRequest(req -> accountService.getAccount(req), id);
    }

    @DeleteMapping("/{id}")
    public Response<Boolean> removeAccount(@PathVariable("id") String id) {
        return processNoRequest(req -> accountService.removeAccount(req), id);
    }

    @PostMapping("/{id}/track")
    public Response<Account> createTrack(@PathVariable("id") String id, @RequestBody Request<Track> request) {
        return process(req -> accountService.createTrack(id, req), request);
    }

    @DeleteMapping("/{accountId}/track/{trackId}")
    public Response<Boolean> deleteTrack(@PathVariable("proxyId") String proxyId, @PathVariable("trackId") String trackId) {
        return processNoRequest(req -> accountService.removeTrack(proxyId, trackId), null);
    }

    @PatchMapping("/{id}/track")
    public Response<Account> updateTrack(@PathVariable("id") String id, @RequestBody Request<Track> request) {
        return process(req -> accountService.updateTrack(id, req), request);
    }

    @PostMapping("/{id}/playlist")
    public Response<Account> createPlaylist(@PathVariable("id") String id, @RequestBody Request<Playlist> request) {
        return process(req -> accountService.createPlaylist(id, req), request);
    }

    @PatchMapping("/{id}/playlist")
    public Response<Account> updatePlaylist(@PathVariable("id") String id, @RequestBody Request<Playlist> request) {
        return process(req -> accountService.updatePlaylist(id, req), request);
    }

    @DeleteMapping("/{accountId}/playlist/{playlistId}")
    public Response<Boolean> deletePlaylist(@PathVariable("proxyId") String proxyId, @PathVariable("playlistId") String playlistId) {
        return processNoRequest(req -> accountService.removePlaylist(proxyId, playlistId), null);
    }

    @PostMapping("/{id}/session/")
    public Response<Account> createSession(@PathVariable("id") String id, @RequestBody Request<Session> request) {
        return process(req -> accountService.createSession(id, req), request);
    }
}