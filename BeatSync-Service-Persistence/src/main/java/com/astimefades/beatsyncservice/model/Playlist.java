package com.astimefades.beatsyncservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Playlist extends Model {

    private String name;
    private List<String> tracks = new ArrayList<>();
    private List<Track> fqTracks = new ArrayList<>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("tracks")
    public List<String> getTracks() {
        return tracks;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }

    @JsonProperty("fqTracks")
    public List<Track> getFqTracks() {
        return fqTracks;
    }

    public void setFqTracks(List<Track> fqTracks) {
        this.fqTracks = fqTracks;
    }
}