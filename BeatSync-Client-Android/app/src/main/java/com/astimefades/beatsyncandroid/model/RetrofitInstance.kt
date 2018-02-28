package com.astimefades.beatsyncandroid.model

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by tobycaulk on 2/26/18.
 */
object RetrofitInstance {

    val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()!!
}