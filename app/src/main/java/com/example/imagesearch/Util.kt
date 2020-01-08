package com.example.imagesearch

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import kotlin.math.roundToInt

object Util {

    var loading : AppCompatDialog? = null

    interface FunUtil {

        /**
         * doubleClick prevent
         */
        fun View.preventDoubleClick(time: Long) = run {
            isEnabled = false
            this.postDelayed({ this.isEnabled = true },time)
        }

    }

    /**
     * Toast
     */
    fun toastShort(msg: String){
        Toast.makeText(App.INSTANCE,msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg: String){
        Toast.makeText(App.INSTANCE,msg, Toast.LENGTH_LONG).show()
    }

    /**
     * keyboard
     */
    fun hideKeyboard() {
        try {
            val focusView = App.currentActivity.currentFocus

            if (focusView != null) {
                val binder = focusView.windowToken
                if (binder != null) {
                    val imm = App.currentActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binder, 0)
                    focusView.clearFocus()
                }
            }
        } catch (e: Exception) {
            Log.e("error -> ",e.message.toString())
        }
    }

    /**
     * loading
     */
    fun showLoading() {
        loading = AppCompatDialog(App.currentActivity).apply {
            setCancelable(false)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_loading)
        }.also { it.show() }
    }

    fun dismissLoading(){
        loading?.dismiss()
    }

    /**
     * dp 변환
     */
    fun dpTopx(dp: Int) : Int{
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            App.INSTANCE.resources.displayMetrics
        ).roundToInt()
    }

    fun pxTodp(px: Int) : Int{
        var density = App.INSTANCE.resources.displayMetrics.density

        when(density){
            1.0f -> density *= 4.0f
            1.5f -> density *= (8/3)
            2.0f -> density *= 2.0f
        }

        return (px / density).roundToInt()
    }

}