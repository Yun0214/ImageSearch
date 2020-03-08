package com.example.imagesearch

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.adapters.TextViewBindingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

object Util {

    var loading : AppCompatDialog? = null

    interface ViewUtil {
        /**
         * doubleClick prevent
         */
        fun View.intervalClick(click: ()-> Unit, interval: Long = 200) {
            var lastClickTime : Long = 0

            setOnClickListener {
                val currentTime = SystemClock.uptimeMillis()
                val elapsedTime = currentTime - lastClickTime
                lastClickTime = currentTime

                if(elapsedTime > interval){
                    click()
                }
            }
        }

        /**
         * afterTextChanged only _ textWatcher
         */
        fun EditText.jobTextWatcher(afterChanged: (s: Editable?)-> Unit) = run {
            addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    afterChanged(s)
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        /**
         * loading dialog
         */
        fun showLoading(activity: AppCompatActivity){
            loading = AppCompatDialog(activity).apply {
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setContentView(R.layout.dialog_loading)
            }.also { it.show() }
        }

        fun dismissLoading(){
            loading?.dismiss()
        }
    }

    /**
     * Toast
     */
    fun toastShort(msg: String){
        Toast.makeText(App.INSTANCE,msg, Toast.LENGTH_SHORT).show()
    }
    fun toastShort(msgId: Int){
        Toast.makeText(App.INSTANCE,App.INSTANCE.resources.getText(msgId), Toast.LENGTH_SHORT).show()
    }

    fun toastLong(msg: String) {
        Toast.makeText(App.INSTANCE, msg, Toast.LENGTH_LONG).show()
    }
    fun toastLong(msgId: Int){
        Toast.makeText(App.INSTANCE,App.INSTANCE.resources.getText(msgId), Toast.LENGTH_LONG).show()
    }


    /**
     * keyboard
     */
    fun hideKeyboard(activity: AppCompatActivity) {
        CoroutineScope(Dispatchers.Main).launch{
            try {
                val focusView = activity.currentFocus

                if (focusView != null) {
                    val binder = focusView.windowToken
                    if (binder != null) {
                        val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binder, 0)
                        focusView.clearFocus()
                    }
                }

            } catch (e: Exception) {
                Log.e("error -> ",e.message.toString())
            }
        }
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