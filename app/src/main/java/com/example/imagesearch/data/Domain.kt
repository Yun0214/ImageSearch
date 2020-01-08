package com.example.imagesearch.data

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.imagesearch.Util

class UsecaseImageSearch {
    lateinit var keyword : ObservableField<String>
    lateinit var model: ObservableField<SearchResultData>
    lateinit var page : ObservableInt

    fun getUserProfileData() {
        Util.showLoading()
        try {
            DataFactory().getSearchResult(keyword.get()?: "",model,page.get())
        }catch (e: Exception){
            Log.e("error -> ",e.message.toString())
        }

    }
}

