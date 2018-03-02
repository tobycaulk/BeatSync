package com.astimefades.beatsyncandroid.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by tobycaulk on 3/2/18.
 */
data class LoginAccountRequest(@JsonProperty("email") val email: String,
                               @JsonProperty("password") val password: String)