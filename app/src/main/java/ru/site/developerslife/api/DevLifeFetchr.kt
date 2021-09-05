package ru.site.developerslife.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.site.developerslife.db.PhotoItem
import ru.site.developerslife.db.PhotoList

private const val TAG = "DevLifeFetchr"

class DevLifeFetchr(tabTitleEn: String) {
    private val devLifeApi: DevLifeApi
    private var count = 0
    private var page = 0
    private var index = -1
    private var photos: MutableList<PhotoItem> = mutableListOf()
    var currentPhotoItem: MutableLiveData<PhotoItem> = MutableLiveData()

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://developerslife.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        devLifeApi = retrofit.create(DevLifeApi::class.java)

    }

    fun fetchContents(): LiveData<List<PhotoItem>> {
        var responseLiveData: MutableLiveData<List<PhotoItem>> = MutableLiveData()
        val devLifeRequest: Call<PhotoResponse> = devLifeApi.fetchContents()
        devLifeRequest.enqueue(object : Callback<PhotoResponse> {
            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(call: Call<PhotoResponse>, response: Response<PhotoResponse>) {
                Log.d(TAG, "Response received")
                //val devLifeResponse: DevLifeResponse? = response.body()
                val photoResponse: PhotoResponse? = response.body()//devLifeResponse?.result
                var photoItems: List<PhotoItem> = photoResponse?.photoItems ?: mutableListOf<>()
                /*photoItems = photoItems.filterNot {
                    it.gifURL.isBlank()
                }*/
                responseLiveData.value = photoItems
            }
        })

        return responseLiveData
    }

    fun isHistoryEmpty(): Boolean = (index <= 0)

    fun nextPhotoList() {
        if (index == photos.count() - 1) {
            loadPage(page + 1)
        } else {
            index++;
            currentPhotoItem.value = photos[index]
        }
    }

    fun previousPhotoList() {
        if (index >= 1) {
            index--;
            currentPhotoItem.value = photos[index]
        }
    }

    fun yetAnotherPost() = (count - index > 1)
}