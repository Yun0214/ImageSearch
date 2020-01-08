package com.example.imagesearch.ui.base

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

open class BaseItemViewModel : ViewModel(){

    var viewType = -1

    lateinit var imageUrl : ObservableField<String>
    lateinit var position : ObservableField<String>
}