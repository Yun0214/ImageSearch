package com.example.imagesearch.ui.search

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.imagesearch.App
import com.example.imagesearch.Util
import com.example.imagesearch.data.ImageSearchRepository
import com.example.imagesearch.data.UsecaseImageSearch

class MainViewModel : ViewModel(), Util.FunUtil {

    val searchFlag = ObservableBoolean()
    val topFlag = ObservableBoolean()

    val imageSearchRepository = ImageSearchRepository()
    val usecaseImageSearch = UsecaseImageSearch()


    fun searchProcess(){
        imageSearchRepository.page.set(1)
        usecaseImageSearch.getSearchResultData(imageSearchRepository)
    }

    fun topClick(v: View){
        v.preventDoubleClick(200)

        topFlag.set(true)
    }

    fun beforeClick(v: View){
        v.preventDoubleClick(150)

        movePage(false)
    }

    fun nextClick(v: View){
        v.preventDoubleClick(150)

        movePage(true)
    }

    private fun movePage(updownFlag:Boolean){

        imageSearchRepository.page.set(imageSearchRepository.page.get() + (if(updownFlag) 1 else -1))
        usecaseImageSearch.getSearchResultData(imageSearchRepository)
    }
}
