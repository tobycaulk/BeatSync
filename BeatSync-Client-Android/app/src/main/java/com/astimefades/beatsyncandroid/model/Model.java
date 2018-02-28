package com.astimefades.beatsyncandroid.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Model {

    private String id;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}