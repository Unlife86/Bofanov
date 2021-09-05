package ru.site.developerslife.api

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.site.developerslife.db.PhotoList

interface DevLifeApi {
    @GET("/{titlesEn}/{devLifePage}?json=true")
    fun fetchContents(
        @Path("titlesEn") titlesEn: String,
        @Path("devLifePage") devLifePage: Int
    ): Single<PhotoList>
}