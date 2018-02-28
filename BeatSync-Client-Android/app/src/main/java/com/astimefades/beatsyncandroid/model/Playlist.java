package com.astimefades.beatsyncandroid.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Playlist extends Model {

    private String name;
    private List<ObjectId> tracks = new ArrayList<>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("tracks")
    public List<ObjectId> getTracks() {
        return tracks;
    }

    public void setTracks(List<ObjectId> tracks) {
        this.tracks = tracks;
    }
}