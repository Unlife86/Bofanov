package ru.site.developerslife.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.site.developerslife.db.TabTitlesEn

class PageModelCreator(private val category: TabTitlesEn) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PageViewModel(category) as T
    }
}