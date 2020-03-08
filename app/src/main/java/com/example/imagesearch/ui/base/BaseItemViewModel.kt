package com.example.imagesearch.ui.base

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

open class BaseItemViewModel : BaseViewModel(){

    var viewType = -1
    var position = -1

    lateinit var imageUrl : String
}