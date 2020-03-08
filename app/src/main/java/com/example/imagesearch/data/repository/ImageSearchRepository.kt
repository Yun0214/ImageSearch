package com.example.imagesearch.data.repository

import com.example.imagesearch.data.DataSource
import com.example.imagesearch.domain.entity.ImageSearchResultData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageSearchRepository(val dataSource: DataSource) {
    var searchKeyword = ""
    var page = 1

    suspend fun getImageSearchResult() : ImageSearchResultData? {
       return withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {

            dataSource.getSearchResult(searchKeyword, page)
       }
    }
}