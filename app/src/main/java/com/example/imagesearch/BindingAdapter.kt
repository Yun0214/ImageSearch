package com.example.imagesearch

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.data.SearchResultData
import com.example.imagesearch.ui.base.BindingRecyclerViewAdapter
import com.example.imagesearch.ui.base.BaseItemViewModel
import java.text.DecimalFormat
import java.util.*
import kotlin.concurrent.timerTask

object BindingAdapter {
    var searchTimer : Timer? = null

    /**
     * edittext input check
     */
    @BindingAdapter("userInput","searchTrigger")
    @JvmStatic
    fun userInputCheck(view: View, keyword: ObservableField<String>,flag: ObservableBoolean){
        if((keyword.get()?: "").isNotBlank() && !flag.get() && view.isFocused){
            searchTimer?.run {
                cancel()
            }
            searchTimer = Timer()
            searchSchedule(flag)
        } else {
            searchTimer?.cancel()
        }
    }

    fun searchSchedule(flag: ObservableBoolean){
        searchTimer?.schedule(timerTask {
            flag.set(true)
        },1000)
    }

    @BindingAdapter("searchTrigger","searchFun")
    @JvmStatic
    fun searchProcess(view: View,flag: ObservableBoolean, process: () -> Unit){
        if(flag.get()){
            Util.hideKeyboard()
            process.invoke()
            flag.set(false)
        }
    }

    /**
     * Image load
     */
    @BindingAdapter("Url")
    @JvmStatic
    fun setImageDefault(view: ImageView, url : ObservableField<String>) {
        Glide.with(view.context).load(url.get()).placeholder(R.drawable.ic_launcher_foreground).into(view)
    }

    /**
     * int -> String
     */
    @BindingAdapter("intToString")
    @JvmStatic
    fun setIntToString(view:TextView, int : Int){
        view.text = DecimalFormat("###,###").format(int)
    }

    /**
     * drag -> top
     */
    @BindingAdapter("topDrag")
    @JvmStatic
    fun dragToTop(view:RecyclerView, flag: ObservableBoolean){
        if(flag.get()){
            view.smoothScrollToPosition(0)
            flag.set(false)
        }
    }

    /**
     * visibility | bool -> visible/gone |
     */
    @BindingAdapter("visibility")
    @JvmStatic
    fun setVisibility(view: View, flag: Boolean) {
        if(flag){ view.visibility = View.VISIBLE }else{ view.visibility = View.GONE }
    }

    /**
     * itemlist Update
     */
    @BindingAdapter("OneModel")
    @JvmStatic
    fun makeModelList(view: RecyclerView, model: ObservableField<*>) {
        when{
            (model.get() is SearchResultData) -> {
                val list = ObservableArrayList<BaseItemViewModel>().apply {
                    for((i,url) in (model.get() as SearchResultData).image_url_list.withIndex()){
                        add(BaseItemViewModel().apply {
                            viewType = Constants.ITEMVIEW_IMAGE
                            imageUrl = ObservableField<String>().apply { set(url) }
                            position = ObservableField<String>().apply { set((i+1).toString()) }
                        })
                    }
                }
                (view.adapter as? BindingRecyclerViewAdapter)?.run {
                    clearList()
                    addList(list as ArrayList<BaseItemViewModel>)
                } ?: run {
                    view.adapter = BindingRecyclerViewAdapter().also {
                        it.addList(list)

                    }
                }
                view.adapter?.notifyDataSetChanged()
                view.postDelayed({
                    view.smoothScrollToPosition(0)
                    Util.dismissLoading()
                },80)

            }
        }
    }
}