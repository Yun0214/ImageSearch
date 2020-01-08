package com.example.imagesearch.data

data class SearchResultData(
    var image_url_list: ArrayList<String>,
    var is_end: Boolean,
    var total_count: Int)

data class metaData(
    var pageable_count : Int,
    var is_end : Boolean
)

data class documentsData(
    var image_url : String
)