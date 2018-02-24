package com.astimefades.beatsyncservice.service;

import com.astimefades.beatsyncservice.model.Account;
import com.astimefades.beatsyncservice.model.Playlist;
import com.astimefades.beatsyncservice.model.Session;
import com.astimefades.beatsyncservice.model.Track;
import com.astimefades.beatsyncservice.model.db.AccountRepository;
import com.astimefades.beatsyncservice.model.error.BeatSyncError;
import com.astimefades.beatsyncservice.model.request.CreateAccountRequest;
import com.astimefades.beatsyncservice.util.PasswordUtil;
import com.astimefades.beatsyncservice.util.Util;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(CreateAccountRequest request) throws Exception {
        if(isEmailTaken(request.getEmail())) {
            throw BeatSyncError.getException(BeatSyncError.EMAIL_TAKEN);
        }

        Account account = new Account();
        account.setEmail(request.getEmail());
        account.setPassword(PasswordUtil.getHash(request.getPassword()));

        return accountRepository.create(account);
    }

    public Account getAccount(ObjectId id) {
        return accountRepository.findOne(id);
    }

    public boolean removeAccount(ObjectId id) {
        return accountRepository.delete(id);
    }

    public Account updateAccount(Account account) {
        return accountRepository.update(account);
    }

    public Account createTrack(ObjectId id, Track track) {
        Account account = getAccount(id);
        if(account != null) {
            track.setId(new ObjectId());
            track.getDetails().setDatesToNow();
            account.addTrack(track);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public boolean removeTrack(ObjectId accountId, ObjectId trackId) {
        Account account = getAccount(accountId);
        if(account != null) {
            Track trackToRemove = Util.removeModelFromListById(account.getTracks(), trackId);

            updateAccount(account);

            return !getAccount(accountId).getTracks().contains(trackToRemove);
        }

        return false;
    }

    public Account updateTrack(ObjectId id, Track track) {
        Account account = getAccount(id);
        if(account != null) {
            Util.removeModelFromListById(account.getTracks(), track.getId());

            track.getDetails().setDatesToNow();
            account.getTracks().add(track);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public Account createPlaylist(ObjectId id, Playlist playlist) {
        Account account = getAccount(id);
        if(account != null) {
            playlist.setId(new ObjectId());
            playlist.getDetails().setDatesToNow();
            account.addPlaylist(playlist);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public Account updatePlaylist(ObjectId id, Playlist playlist) {
        Account account = getAccount(id);
        if(account != null) {
            Util.removeModelFromListById(account.getPlaylists(), playlist.getId());

            playlist.getDetails().setDatesToNow();
            account.getPlaylists().add(playlist);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public boolean removePlaylist(ObjectId accountId, ObjectId playlistId) {
        Account account = getAccount(accountId);
        if(account != null) {
            Playlist playlistToRemove = Util.removeModelFromListById(account.getPlaylists(), playlistId);

            updateAccount(account);

            return !getAccount(accountId).getPlaylists().contains(playlistToRemove);
        }

        return false;
    }

    public Account createSession(ObjectId id, Session session) {
        Account account = getAccount(id);
        if(account != null) {
            session.setId(new ObjectId());
            account.setSession(session);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public boolean isEmailTaken(String email) {
        return accountRepository.findByEmail(email) != null;
    }
}