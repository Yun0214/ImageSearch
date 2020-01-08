package com.example.imagesearch.ui.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindingViewHolder<T : ViewDataBinding>(view: View,val type: Int? = -1) : RecyclerView.ViewHolder(view) {
    private val binding: T? = DataBindingUtil.bind(view)

    fun binding(): T? {
        return binding
    }
}

