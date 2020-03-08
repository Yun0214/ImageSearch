package com.example.imagesearch.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.Util
import kotlinx.coroutines.channels.SendChannel

open class BaseViewModel : ViewModel() {

    var viewUpdateActor : SendChannel<Int>? = null
    var loadingActor : SendChannel<Boolean>? = null
    var dragActor : SendChannel<Int>? = null

    val vmScope by lazy {
        viewModelScope
    }

}