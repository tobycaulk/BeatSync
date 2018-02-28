package com.astimefades.beatsyncandroid.model.request

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by tobycaulk on 2/27/18.
 */
data class Request<T>(@JsonProperty("payload") var payload: T)