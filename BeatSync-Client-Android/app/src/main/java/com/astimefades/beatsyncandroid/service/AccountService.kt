package com.astimefades.beatsyncandroid.service

import com.astimefades.beatsyncandroid.model.Playlist
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
    @POST("account/logout/{id}")
    fun logoutAccount(@Path("id") proxyId: String): Call<Response<Boolean>>

    @Headers("Content-Type: application/json")
    @GET("account/{id}/track/all")
    fun getAllTracks(@Path("id") id: String): Call<Response<List<Track>>>

    @Headers("Content-Type: application/json")
    @GET("account/{proxyId}/track/{id}")
    fun getTrack(@Path("proxyId") proxyId: String, @Path("id") id: String): Call<Response<Track>>

    @Headers("Content-Type: application/json")
    @PATCH("account/{id}/track/")
    fun saveTrack(@Path("id") proxyId: String, @Body request: Request<Track>): Call<Response<Track>>

    @Headers("Content-Type: application/json")
    @POST("account/{id}/track/")
    fun createTrack(@Path("id") proxyId: String, @Body request: Request<Track>): Call<Response<Track>>

    @Headers("Content-Type: application/json")
    @GET("account/{id}/playlist/all")
    fun getAllPlaylists(@Path("id") id: String): Call<Response<List<Playlist>>>

    @Headers("Content-Type: application/json")
    @GET("account/{proxyId}/playlist/{id}")
    fun getPlaylist(@Path("proxyId") proxyId: String, @Path("id") id: String): Call<Response<Playlist>>

    @Headers("Content-Type: application/json")
    @PATCH("account/{id}/playlist/")
    fun savePlaylist(@Path("id") proxyId: String, @Body request: Request<Playlist>): Call<Response<Playlist>>

    @Headers("Content-Type: application/json")
    @POST("account/{id}/playlist/")
    fun createPlaylist(@Path("id") proxyId: String, @Body request: Request<Playlist>): Call<Response<Playlist>>
}