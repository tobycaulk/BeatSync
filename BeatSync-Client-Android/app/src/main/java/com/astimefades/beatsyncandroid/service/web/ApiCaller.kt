package com.astimefades.beatsyncandroid.service.web

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.model.response.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by tobycaulk on 3/7/18.
 */
open class ApiCaller<out T> (webServiceType: Class<T>) {

    protected val webService: T

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(JacksonConverterFactory.create())
                .build()!!

        webService = retrofit.create(webServiceType)
    }

    fun<T, R> send(request: Request<T>, send: (Request<T>) -> Call<Response<R>>, success: (R) -> Unit, activity: Activity) {
        send(request, send, success, { _, _ -> /* Do nothing on failure by default (besides display Toast) */ }, { /* Do nothing on timeout by default (besides display Toast) */ }, activity)
    }

    fun<T, R> send(request: Request<T>, send: (Request<T>) -> Call<Response<R>>, success: (R) -> Unit, failure: (String, Int) -> Unit, activity: Activity) {
        send(request, send, success, failure, { /* Do nothing on timeout by default (besides display Toast) */ }, activity)
    }

    fun<T, R> send(request: Request<T>, send: (Request<T>) -> Call<Response<R>>, success: (R) -> Unit, failure: (String, Int) -> Unit, error: () -> Unit, activity: Activity) {
        doAsync {
            try {
                val response = send(request).execute().body()
                uiThread {
                    if (response != null) {
                        if (response.errorDescription != null) {
                            handleFailure(activity, failure, response.errorDescription, response.errorNumber!!)
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

    private fun handleFailure(activity: Activity, failure: (String, Int) -> Unit, errorDescription: String, errorNumber: Int) {
        Log.e("ApiCaller", errorDescription)
        failure(errorDescription, errorNumber)
        Toast.makeText(activity, "Unexpected error! Please try again.", Toast.LENGTH_LONG).show()
    }

    private fun handleError(activity: Activity, timeout: () -> Unit) {
        timeout()
        Toast.makeText(activity, "Unexpected error! Please try again.", Toast.LENGTH_LONG).show()
    }
}