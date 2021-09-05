package ru.site.developerslife.db

data class PhotoList (
    val result: List<PhotoItem>,
    val totalCount: Int
)