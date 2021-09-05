package ru.site.developerslife.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import ru.site.developerslife.db.PhotoItem

private const val BASE_URL = "https://developerslife.ru/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface DisplayDataApiService {
    @GET("random?json=true")
    suspend fun getProperties(): List<PhotoItem>
}
object DataApi {
    val retrofitService : DisplayDataApiService by lazy {
        retrofit.create(DisplayDataApiService::class.java) }
}