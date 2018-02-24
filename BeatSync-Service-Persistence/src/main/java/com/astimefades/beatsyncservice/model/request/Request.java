package com.astimefades.beatsyncservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Request<T> {

    private T payload;

    @JsonProperty("payload")
    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}