package com.astimefades.beatsyncandroid.web

import com.astimefades.beatsyncandroid.model.Account
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.model.response.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by tobycaulk on 2/28/18.
 */
class PersistenceApi {

    private val accountService: AccountService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()!!

        accountService = retrofit.create(AccountService::class.java)
    }

    fun loginAccount(request: Request<LoginAccountRequest>) = accountService.loginAccount(request)

    fun createAccount(request: Request<CreateAccountRequest>) = accountService.createAccount(request)
}