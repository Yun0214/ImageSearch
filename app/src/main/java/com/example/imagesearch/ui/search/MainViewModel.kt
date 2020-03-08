package com.example.imagesearch.ui.search

import android.text.Editable
import com.example.imagesearch.data.repository.ImageSearchRepository
import com.example.imagesearch.domain.entity.ImageSearchDocumentsData
import com.example.imagesearch.domain.entity.ImageSearchMetaData
import com.example.imagesearch.domain.entity.ImageSearchResultData
import com.example.imagesearch.domain.usecase.GetImageSearchData
import com.example.imagesearch.ui.base.BaseItemViewModel
import com.example.imagesearch.ui.base.BaseViewModel
import com.example.imagesearch.ui.base.BindingRecyclerViewAdapter
import com.example.imagesearch.ui.mapper.ImageSearchDocumentMapper
import kotlinx.coroutines.*


class MainViewModel(private val imageSearchRepository : ImageSearchRepository) : BaseViewModel() {

    private val getImageSearchData = GetImageSearchData()
    private var searchProcess : Job? = null

    val imageAdapter = BindingRecyclerViewAdapter()
    var total = ""
    var nextPageFlag = false
    var page = ""

    fun searchTextWatcher(t: Editable?){
        vmScope.launch {
            if((t?: " ").isNotBlank()) {
                searchProcess?.run { if(isActive) cancel() }
                searchProcess = searchScope(t.toString())
            } else {
                searchProcess?.run { if(isActive) cancel() }
            }
        }
    }

    fun pageMoveClick(nextFlag: Boolean){
        vmScope.launch {
            callDrag()
            searchScope(updownFlag = nextFlag)
        }
    }

    fun topClick(){
        callDrag()
    }

    private suspend fun searchScope(keyword : String = "", updownFlag: Boolean? = null) = vmScope.launch {
        if(keyword != "") delay(1000)

        callLoadingDialog(true)

        getSearchResult(updownFlag,keyword)?.let {
            callLoadingDialog(false)
            callDrag()
            callViewUpdate(it)
        } ?: run {
            callLoadingDialog(false)
        }
    }

    private suspend fun getSearchResult(updownFlag: Boolean?, keyword: String) : ImageSearchResultData?{

        return getImageSearchData.getSearchResultData(imageSearchRepository.apply {
            page = when(updownFlag){
                true -> page + 1
                false -> page - 1
                else -> {
                    searchKeyword = keyword
                    1
                }
            }
        })
    }


    private fun callLoadingDialog(flag: Boolean){
        loadingActor?.offer(flag)
    }

    private fun callDrag(){
        dragActor?.offer(0)
    }

    private fun callViewUpdate(data: ImageSearchResultData){
        vmDataUpdate(data.meta)
        adapterListUp(data.documents)

        viewUpdateActor?.offer(0)
    }

    private fun vmDataUpdate(data : ImageSearchMetaData){
        data.run {
            total = pageable_count.toString()
            nextPageFlag = !is_end
            page = imageSearchRepository.page.toString()
        }
    }

    private fun adapterListUp(list : ArrayList<ImageSearchDocumentsData>){
        imageAdapter.run {
            clearList()
            addList( makeItemVmList(list) )
            notifyDataSetChanged()
        }
    }

    private fun makeItemVmList(list: ArrayList<ImageSearchDocumentsData>) : ArrayList<BaseItemViewModel>{
        return ArrayList<BaseItemViewModel>().apply {
            for((i,entity) in list.withIndex()){
                add(ImageSearchDocumentMapper().fromEntity(entity).also {
                    it.position = i+1
                })
            }
        }
    }
}
