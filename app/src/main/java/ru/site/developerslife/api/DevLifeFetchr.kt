package ru.site.developerslife.api

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.site.developerslife.db.PhotoItem
import ru.site.developerslife.db.PhotoList
import ru.site.developerslife.db.TabTitlesEn

private const val TAG = "DevLifeFetchr"

class DevLifeFetchr(private val category: TabTitlesEn) {
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

    /*fun fetchContents(devLifePage: Int): LiveData<List<PhotoItem>> {
        var responseLiveData: MutableLiveData<List<PhotoItem>> = MutableLiveData()
        val devLifeRequest: Call<PhotoResponse> = devLifeApi.fetchContents(titlesEn = "", devLifePage = devLifePage)
        devLifeRequest.enqueue(object : Callback<PhotoResponse> {
            override fun onFailure(call: Call<PhotoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(call: Call<PhotoResponse>, response: Response<PhotoResponse>) {
                Log.d(TAG, "Response received")
                //val devLifeResponse: DevLifeResponse? = response.body()
                val photoResponse: PhotoResponse? = response.body()//devLifeResponse?.result
                var photoItems: List<PhotoItem> = photoResponse?.photoItems ?: mutableListOf()
                /*photoItems = photoItems.filterNot {
                    it.gifURL.isBlank()
                }*/
                responseLiveData.value = photoItems
            }
        })

        return responseLiveData
    }*/

    @SuppressLint("CheckResult")
    fun loadPage(devLifePage: Int) {
        devLifeApi.fetchContents(titlesEn = category.title, devLifePage = devLifePage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { page ->
                    count = page.totalCount
                    if (count == 0) {
                        return@subscribe
                    }
                    for (post in page.result) {
                       photos.add(post)
                    }

                    index++;
                    currentPhotoItem = devLifePage
                    currentPhotoItem.value = photos[index]
                },
                { _ -> Log.d("Session", "Failed to fetch photos = " + category.category) }
            )
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