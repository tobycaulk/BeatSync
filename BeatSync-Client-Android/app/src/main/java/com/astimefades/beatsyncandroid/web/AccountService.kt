package com.astimefades.beatsyncandroid.web

import com.astimefades.beatsyncandroid.model.Account
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.model.response.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Created by tobycaulk on 2/28/18.
 */
interface AccountService {

    @Headers("Content-Type: application/json")
    @POST("account/")
    fun createAccount(@Body request: Request<CreateAccountRequest>): Call<Response<Account>>

    @Headers("Content-Type: application/json")
    @POST("account/login")
    fun loginAccount(@Body request: Request<LoginAccountRequest>): Call<Response<Account>>

}