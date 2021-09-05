package ru.site.developerslife.api

import com.google.gson.annotations.SerializedName
import ru.site.developerslife.db.PhotoItem
import ru.site.developerslife.db.PhotoList

class PhotoResponse {
    @SerializedName("result")
    lateinit var photoItems: List<PhotoItem>
}