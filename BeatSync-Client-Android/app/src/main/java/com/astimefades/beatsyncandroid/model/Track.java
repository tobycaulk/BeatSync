package com.astimefades.beatsyncandroid.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Track extends Model {

    private String name;
    private int bpm;
    private int length;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("bpm")
    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    @JsonProperty("length")
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}