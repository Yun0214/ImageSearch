package com.example.nrise.ui.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    //activity 등록
    abstract fun setCurrentActivity()

    override fun onResume() {
        super.onResume()
        setCurrentActivity()
    }
}