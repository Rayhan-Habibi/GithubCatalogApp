package com.example.mandarinkatalog.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceholderAPI {
    @GET("{github}/repos")
    fun getRepoByUsername(
        @Path("github") github: String
    ): Call<List<ResponseItem>>
}
