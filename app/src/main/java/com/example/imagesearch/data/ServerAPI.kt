package com.example.imagesearch.data

import com.example.imagesearch.Constants
import com.example.imagesearch.domain.entity.ImageSearchResultData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ServerAPI {

    @GET(Constants.KAKAO_IMAGE_SEARCH_URL)
    suspend fun getSearchResultData(
        @Header("Authorization" ) key: String,
        @Query("query") keyword: String,
        @Query("page") page: Int,
        @Query("size") size: Int) : ImageSearchResultData
}