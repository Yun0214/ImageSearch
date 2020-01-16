package com.example.imagesearch.data

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt

class ImageSearchRepository(){
    val searchResultDataModel = ObservableField<SearchResultData>()
    val keyword = ObservableField<String>()
    val page = ObservableInt().apply { set(1) }
}