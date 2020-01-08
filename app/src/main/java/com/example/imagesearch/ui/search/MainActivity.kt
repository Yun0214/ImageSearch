package com.example.imagesearch.ui.search

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.imagesearch.App
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.nrise.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun setCurrentActivity() {
        App.currentActivity = this
    }

    lateinit var binding : ActivityMainBinding
    var vm : MainViewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.vm = vm
    }
}
