package com.astimefades.beatsyncservice.service;

import com.astimefades.beatsyncservice.model.Account;
import com.astimefades.beatsyncservice.model.Playlist;
import com.astimefades.beatsyncservice.model.Session;
import com.astimefades.beatsyncservice.model.Track;
import com.astimefades.beatsyncservice.model.db.AccountRepository;
import com.astimefades.beatsyncservice.model.error.BeatSyncError;
import com.astimefades.beatsyncservice.model.error.BeatSyncException;
import com.astimefades.beatsyncservice.model.request.CreateAccountRequest;
import com.astimefades.beatsyncservice.model.request.LoginAccountRequest;
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

    public Account loginAccount(LoginAccountRequest request) throws BeatSyncException {
        Account account = getAccountByEmail(request.getEmail());
        if(account != null) {
            if(PasswordUtil.checkPassword(request.getPassword(), account.getPassword())) {
                return account;
            } else {
                throw BeatSyncError.getException(BeatSyncError.INVALID_PASSWORD_FOR_ACCOUNT);
            }
        } else {
            throw BeatSyncError.getException(BeatSyncError.EMAIL_NOT_FOUND);
        }
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

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account getAccount(String id) {
        return accountRepository.findOne(id);
    }

    public boolean removeAccount(String id) {
        return accountRepository.delete(id);
    }

    public Account updateAccount(Account account) {
        return accountRepository.update(account);
    }

    public Account createTrack(String id, Track track) {
        Account account = getAccount(id);
        if(account != null) {
            track.setId(new ObjectId().toString());
            account.addTrack(track);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public boolean removeTrack(String accountId, String trackId) {
        Account account = getAccount(accountId);
        if(account != null) {
            Track trackToRemove = Util.removeModelFromListById(account.getTracks(), trackId);

            updateAccount(account);

            return !getAccount(accountId).getTracks().contains(trackToRemove);
        }

        return false;
    }

    public Account updateTrack(String id, Track track) {
        Account account = getAccount(id);
        if(account != null) {
            Util.removeModelFromListById(account.getTracks(), track.getId());

            account.getTracks().add(track);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public Account createPlaylist(String id, Playlist playlist) {
        Account account = getAccount(id);
        if(account != null) {
            playlist.setId(new ObjectId().toString());
            account.addPlaylist(playlist);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public Account updatePlaylist(String id, Playlist playlist) {
        Account account = getAccount(id);
        if(account != null) {
            Util.removeModelFromListById(account.getPlaylists(), playlist.getId());

            account.getPlaylists().add(playlist);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public boolean removePlaylist(String accountId, String playlistId) {
        Account account = getAccount(accountId);
        if(account != null) {
            Playlist playlistToRemove = Util.removeModelFromListById(account.getPlaylists(), playlistId);

            updateAccount(account);

            return !getAccount(accountId).getPlaylists().contains(playlistToRemove);
        }

        return false;
    }

    public Account createSession(String id, Session session) {
        Account account = getAccount(id);
        if(account != null) {
            session.setId(new ObjectId().toString());
            account.setSession(session);

            updateAccount(account);
        }

        return getAccount(id);
    }

    public boolean isEmailTaken(String email) {
        return accountRepository.findByEmail(email) != null;
    }
}