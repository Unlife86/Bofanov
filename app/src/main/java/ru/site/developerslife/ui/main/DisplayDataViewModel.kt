package ru.site.developerslife.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.site.developerslife.api.DataApi
import ru.site.developerslife.db.PhotoItem

class DisplayDataViewModel: ViewModel() {
    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    private val _property = MutableLiveData<PhotoItem>()

    val property: LiveData<PhotoItem>
        get() = _property

    init {
        getDetails()
    }

    private fun getDetails() {
        viewModelScope.launch {
            try {
                val listResult = DataApi.retrofitService.getProperties()
                if (listResult.isNotEmpty()) {
                    _property.value = listResult[0]
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}