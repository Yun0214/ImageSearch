package com.example.imagesearch.util

import com.example.imagesearch.data.DataSource
import com.example.imagesearch.data.repository.ImageSearchRepository
import com.example.imagesearch.ui.search.MainViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var modelPart = module {
    factory {
        ImageSearchRepository( get() )
    }
}

var viewModelPart = module {
    viewModel {
        MainViewModel( get() )
    }
}

var singlePart = module {
    single {
        DataSource()
    }
}

var diModule = listOf(modelPart, viewModelPart, singlePart)
