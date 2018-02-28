package com.astimefades.beatsyncandroid.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by tobycaulk on 2/26/18.
 */

public class CreateAccountRequest {

    private String email;
    private String password;

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
}