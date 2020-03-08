package com.example.imagesearch.data

import android.util.Log
import com.example.imagesearch.App
import com.example.imagesearch.Constants
import com.example.imagesearch.Util
import com.example.imagesearch.domain.entity.ImageSearchResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource : Util.ViewUtil{

    suspend fun getSearchResult(keyword:String, page:Int) : ImageSearchResultData?{

        return try{
            App.RetrofitBuilder.retrofitBuilder()
                .getSearchResultData(Constants.KAKAO_APPKEY,keyword,page,Constants.IMAGE_SEARCH_RESULT_SIZE)
        } catch (e: Exception){
            defaultErrorNoti(e.toString())
            null
        }
    }

    private suspend fun defaultErrorNoti(errorLog: String, errorMsg: String = ""){
        Log.e("server error catch -> ",errorLog)

        dismissLoading()
        withContext(Dispatchers.Main){
            Util.toastShort( if(errorMsg == ""){ "오류가 발생했습니다.\n다시 시도해주세요." } else errorMsg )
        }
    }
}