package com.example.imagesearch.ui.search

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.example.imagesearch.App
import com.example.imagesearch.Util
import com.example.imagesearch.data.SearchResultData
import com.example.imagesearch.data.UsecaseImageSearch

class MainViewModel : ViewModel(), Util.FunUtil {


    val searchResultDataModel = ObservableField<SearchResultData>()
    val keyword = ObservableField<String>()
    val page = ObservableInt().apply { set(1) }
    val searchFlag = ObservableBoolean()

    val useCaseImageSearch = ObservableField<UsecaseImageSearch>().apply {
        set(UsecaseImageSearch().also {
            it.keyword = keyword
            it.model = searchResultDataModel
            it.page = page
        })
    }

    fun topClick(v: View){
        v.preventDoubleClick(200)

        (App.currentActivity as? MainActivity)?.binding?.recyclerView?.smoothScrollToPosition(0)
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

        page.set( page.get() + (if(updownFlag) 1 else -1) )
        (useCaseImageSearch.get() as UsecaseImageSearch).getSearchResultData()
    }
}
