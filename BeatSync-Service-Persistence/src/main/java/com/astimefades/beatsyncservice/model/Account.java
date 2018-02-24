package com.astimefades.beatsyncservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Account extends Model {

    private String email;
    private String password;
    private List<Track> tracks = new ArrayList<>();
    private List<Playlist> playlists = new ArrayList<>();
    private Session session;

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("tracks")
    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    @JsonProperty("playlists")
    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    @JsonProperty("session")
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}