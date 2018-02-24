package com.astimefades.beatsyncservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T> {

    private T payload;
    private int errorNumber;
    private String errorDescription;

    public Response() {}

    public Response(T payload) {
        this.payload = payload;
    }

    @JsonProperty("payload")
    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @JsonProperty("errorNumber")
    public int getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(int errorNumber) {
        this.errorNumber = errorNumber;
    }

    @JsonProperty("errorDescription")
    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}