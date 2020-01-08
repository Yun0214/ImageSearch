package com.example.imagesearch.ui.base

import android.view.*
import androidx.core.view.updateLayoutParams
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearch.Constants
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ItemImgaeBinding

open class BindingRecyclerViewAdapter : RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {
    private var viewTypeList = ArrayList<Int>()
    var list = ArrayList<BaseItemViewModel>()

    fun addList(itemViewModels: ArrayList<BaseItemViewModel>) {
        for (item in itemViewModels) {
            list.add(item)
            viewTypeList.add(item.viewType)
        }
    }

    fun clearList(){
        list.clear()
        viewTypeList.clear()
    }

    override fun getItemCount(): Int {
        return viewTypeList.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewTypeList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> {
        val inflater = LayoutInflater.from(parent.context)
        var layout = R.layout.item_imgae

        when (viewType) {
            //listItem
            Constants.ITEMVIEW_IMAGE ->
                layout = R.layout.item_imgae
        }

        return BindingViewHolder(inflater.inflate(layout,parent,false),viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        val bind = holder.binding()

        when (viewTypeList[position]) {
            Constants.ITEMVIEW_IMAGE -> { bind as ItemImgaeBinding
                bind.vm = list[position]
            }
        }
    }
}


