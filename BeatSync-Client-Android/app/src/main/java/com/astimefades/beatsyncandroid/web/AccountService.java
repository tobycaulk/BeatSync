package com.astimefades.beatsyncandroid.web;

import com.astimefades.beatsyncandroid.model.Account;
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest;
import com.astimefades.beatsyncandroid.model.request.Request;
import com.astimefades.beatsyncandroid.model.response.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by tobycaulk on 2/26/18.
 */

public interface AccountService {

    @Headers("Content-Type: application/json")
    @POST("account/")
    Call<Response<Account>> createAccount(@Body Request<CreateAccountRequest> request);

}