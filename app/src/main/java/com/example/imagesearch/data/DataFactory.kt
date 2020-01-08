package com.example.imagesearch.data

import androidx.databinding.ObservableField
import com.example.imagesearch.App
import com.example.imagesearch.Constants
import com.example.imagesearch.Util
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataFactory {

    //프로필 data _ 단일
    fun getSearchResult(keyword:String, model : ObservableField<SearchResultData>, page:Int) {

        App.RetrofitBuilder.retrofitBuilder()
            .getProfileData(Constants.KAKAO_APPKEY,keyword,page,Constants.IMAGE_SEARCH_RESULT_SIZE)
            .enqueue(object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Util.dismissLoading()
                    Util.hideKeyboard()
                    Util.toastShort("오류가 발생했습니다.\n다시 시도해주세요.")
                }
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    response.body()?.let {
                        val meta = Gson().fromJson(it.getAsJsonObject("meta"),metaData::class.java)
                        val list = ArrayList<String>().apply {
                            for(json in it.getAsJsonArray("documents")){
                                add(Gson().fromJson(json,documentsData::class.java).image_url)
                            }
                        }
                        if(meta.pageable_count == 0){ Util.toastShort("검색 결과가 없습니다.") }
                        model.set( SearchResultData(list,meta.is_end,meta.pageable_count) )

                    } ?: run {
                        Util.dismissLoading()
                        Util.hideKeyboard()
                        Util.toastShort("오류가 발생했습니다.\n다시 시도해주세요.")
                    }
                }
            })
    }

}