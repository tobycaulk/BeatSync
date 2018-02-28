package com.astimefades.beatsyncandroid.model.response

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by tobycaulk on 2/27/18.
 */
data class Response<T>(@JsonProperty("payload") val payload: T,
                       @JsonProperty("errorNumber") val errorNumber: Int??,
                       @JsonProperty("errorDescription") val errorDescription: String??)