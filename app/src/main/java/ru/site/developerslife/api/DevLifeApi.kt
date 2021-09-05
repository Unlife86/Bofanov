package ru.site.developerslife.api

import retrofit2.Call
import retrofit2.http.GET

interface DevLifeApi {
    @GET("random?json=true")
    fun fetchContents(): Call<PhotoResponse>
}