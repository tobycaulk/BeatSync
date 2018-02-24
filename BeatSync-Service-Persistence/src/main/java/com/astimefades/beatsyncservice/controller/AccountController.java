package com.astimefades.beatsyncservice.controller;

import com.astimefades.beatsyncservice.model.Account;
import com.astimefades.beatsyncservice.model.Playlist;
import com.astimefades.beatsyncservice.model.Session;
import com.astimefades.beatsyncservice.model.Track;
import com.astimefades.beatsyncservice.model.request.CreateAccountRequest;
import com.astimefades.beatsyncservice.model.request.Request;
import com.astimefades.beatsyncservice.model.response.Response;
import com.astimefades.beatsyncservice.service.AccountService;
import org.bson.types.ObjectId;
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

    @PostMapping("/")
    public Response<Account> createAccount(@RequestBody Request<CreateAccountRequest> request) {
        return process(req -> accountService.createAccount(req), request);
    }

    @GetMapping("/{id}")
    public Response<Account> getAccount(@PathVariable("id") String id) {
        return processNoRequest(req -> accountService.getAccount(req), new ObjectId(id));
    }

    @DeleteMapping("/{id}")
    public Response<Boolean> removeAccount(@PathVariable("id") String id) {
        return processNoRequest(req -> accountService.removeAccount(req), new ObjectId(id));
    }

    @PostMapping("/{id}/track")
    public Response<Account> createTrack(@PathVariable("id") String id, @RequestBody Request<Track> request) {
        return process(req -> accountService.createTrack(new ObjectId(id), req), request);
    }

    @DeleteMapping("/{accountId}/track/{trackId}")
    public Response<Boolean> deleteTrack(@PathVariable("accountId") String accountId, @PathVariable("trackId") String trackId) {
        return processNoRequest(req -> accountService.removeTrack(new ObjectId(accountId), new ObjectId(trackId)), null);
    }

    @PatchMapping("/{id}/track")
    public Response<Account> updateTrack(@PathVariable("id") String id, @RequestBody Request<Track> request) {
        return process(req -> accountService.updateTrack(new ObjectId(id), req), request);
    }

    @PostMapping("/{id}/playlist")
    public Response<Account> createPlaylist(@PathVariable("id") String id, @RequestBody Request<Playlist> request) {
        return process(req -> accountService.createPlaylist(new ObjectId(id), req), request);
    }

    @PatchMapping("/{id}/playlist")
    public Response<Account> updatePlaylist(@PathVariable("id") String id, @RequestBody Request<Playlist> request) {
        return process(req -> accountService.updatePlaylist(new ObjectId(id), req), request);
    }

    @DeleteMapping("/{accountId}/playlist/{playlistId}")
    public Response<Boolean> deletePlaylist(@PathVariable("accountId") String accountId, @PathVariable("playlistId") String playlistId) {
        return processNoRequest(req -> accountService.removePlaylist(new ObjectId(accountId), new ObjectId(playlistId)), null);
    }

    @PostMapping("/{id}/session/")
    public Response<Account> createSession(@PathVariable("id") String id, @RequestBody Request<Session> request) {
        return process(req -> accountService.createSession(new ObjectId(id), req), request);
    }
}