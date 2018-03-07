package com.astimefades.beatsyncandroid.service

import com.astimefades.beatsyncandroid.model.Track
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.model.response.Response
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by tobycaulk on 2/28/18.
 */
interface AccountService {

    @Headers("Content-Type: application/json")
    @POST("account/")
    fun createAccount(@Body request: Request<CreateAccountRequest>): Call<Response<String>>

    @Headers("Content-Type: application/json")
    @POST("account/login")
    fun loginAccount(@Body request: Request<LoginAccountRequest>): Call<Response<String>>

    @Headers("Content-Type: application/json")
    @POST("account/login/check")
    fun checkAccountLogin(@Body request: Request<String>): Call<Response<String>>

    @Headers("Content-Type: application/json")
    @GET("account/{id}/track/all")
    fun getAllTracks(@Path("id") id: String): Call<Response<List<Track>>>
}