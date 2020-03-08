package com.example.imagesearch.domain.usecase

import com.example.imagesearch.data.DataSource
import com.example.imagesearch.data.repository.ImageSearchRepository
import com.example.imagesearch.domain.entity.ImageSearchResultData
import kotlinx.coroutines.*

class GetImageSearchData {

    suspend fun getSearchResultData(repository: ImageSearchRepository) : ImageSearchResultData? {
        return repository.getImageSearchResult()
    }
}