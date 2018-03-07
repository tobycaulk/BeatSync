package com.astimefades.beatsyncandroid.web

import android.app.Activity
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.Model
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.model.response.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by tobycaulk on 3/4/18.
 */
object PersistenceApi {

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

    fun checkAccountLogin(request: Request<String>) = accountService.checkAccountLogin(request)

    fun<T, R> send(request: Request<T>, send: (Request<T>) -> Call<Response<R>>, success: (R) -> Unit, failure: (String, Int) -> Unit, activity: Activity) {
        send(request, send, success, failure, { /*Do nothing on timeout by default (besides display Toast) */ }, activity)
    }

    fun<T, R> send(request: Request<T>, send: (Request<T>) -> Call<Response<R>>, success: (R) -> Unit, failure: (String, Int) -> Unit, error: () -> Unit, activity: Activity) {
        doAsync {
            try {
                val response = send(request).execute().body()
                uiThread {
                    if (response != null) {
                        if (response.errorDescription != null) {
                            failure(response.errorDescription, response.errorNumber!!)
                        } else {
                            success(response.payload)
                        }
                    } else {
                        handleError(activity, error)
                    }
                }
            } catch(e: Exception) {
                uiThread {
                    handleError(activity, error)
                }
            }
        }
    }

    fun handleError(activity: Activity, timeout: () -> Unit) {
        timeout()
        Toast.makeText(activity, "Unexpected error! Please try again.", Toast.LENGTH_LONG).show()
    }
}