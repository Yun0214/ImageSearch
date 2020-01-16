package com.example.imagesearch.data

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.imagesearch.Util

class UsecaseImageSearch {

    fun getSearchResultData(repository: ImageSearchRepository) {
        Util.showLoading()
        try {
            DataFactory().getSearchResult(repository.keyword.get()?: "",repository.searchResultDataModel,repository.page.get())
        }catch (e: Exception){
            Log.e("error -> ",e.message.toString())
        }
    }
}

