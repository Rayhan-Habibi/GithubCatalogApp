package com.example.mandarinkatalog.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RetrofitClient {
    private const val BASE_URL = "https://api.github.com/users/"

    val instance: PlaceholderAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlaceholderAPI::class.java)

    }
}
