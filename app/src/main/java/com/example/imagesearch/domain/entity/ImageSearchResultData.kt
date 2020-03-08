package com.example.imagesearch.domain.entity


data class ImageSearchResultData(
    var documents: ArrayList<ImageSearchDocumentsData>,
    var meta: ImageSearchMetaData )