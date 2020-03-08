package com.example.imagesearch.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.imagesearch.Util

abstract class BaseActivity<T: ViewDataBinding, R : BaseViewModel> : AppCompatActivity(), Util.ViewUtil {

    lateinit var binding: T

    val activityLifecycleScope by lazy {
        lifecycleScope
    }

    abstract val layoutId: Int
    abstract val vm: R

    abstract fun initLayout()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,layoutId)

        initLayout()
    }
}