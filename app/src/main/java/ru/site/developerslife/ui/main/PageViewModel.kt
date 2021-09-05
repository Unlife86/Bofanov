package ru.site.developerslife.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.site.developerslife.api.DevLifeFetchr
import ru.site.developerslife.db.PhotoItem
import ru.site.developerslife.db.PhotoList
import ru.site.developerslife.db.TabTitlesEn


class PageViewModel(tabTitleEn: TabTitlesEn) : ViewModel() {
    enum class Category(val category: String) {
        LATEST("latest"),
        BESTS("top"),
        HOTS("hot")
    }
    private val _index = MutableLiveData<Int>()

    private val devLifeFetchr = DevLifeFetchr(tabTitleEn)

    fun getData(): LiveData<PhotoItem> = devLifeFetchr.currentPhotoItem
    fun isHistoryEmpty() = devLifeFetchr.isHistoryEmpty()
    fun next() = devLifeFetchr.nextPhotoList()
    fun previous() = devLifeFetchr.previousPhotoList()
    fun yetAnotherPost() = devLifeFetchr.yetAnotherPost()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}