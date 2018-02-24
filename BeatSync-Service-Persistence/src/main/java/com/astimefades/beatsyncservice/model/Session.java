package com.astimefades.beatsyncservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Session extends Model {

    private String name;
    private String pin;
    private List<ObjectId> tracks = new ArrayList<>();
    private Playlist playlist;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("pin")
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @JsonProperty("tracks")
    public List<ObjectId> getTracks() {
        return tracks;
    }

    public void setTracks(List<ObjectId> tracks) {
        this.tracks = tracks;
    }

    @JsonProperty("playlist")
    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}