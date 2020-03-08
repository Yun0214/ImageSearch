package com.example.imagesearch.ui.search

import android.view.View
import android.widget.EditText
import com.example.imagesearch.R
import com.example.imagesearch.Util
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.ui.base.BaseActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import org.koin.androidx.viewmodel.ext.android.viewModel

@ObsoleteCoroutinesApi
class MainActivity : BaseActivity<ActivityMainBinding,MainViewModel>() {

    override val layoutId: Int
        get() = R.layout.activity_main
    override val vm: MainViewModel by viewModel()

    var focusFlag = false

    override fun initLayout() {

        searchEditTextInit(binding.etSearch)

        viewUpdateActorInit()

        loadingActorInit()

        dragActorInit()

        listInit()

        bntClickListenersInit()
    }

    private fun searchEditTextInit(et: EditText){

        et.jobTextWatcher(vm::searchTextWatcher)
        et.setOnFocusChangeListener { _, hasFocus ->
            focusFlag = if(focusFlag && !hasFocus){
                Util.hideKeyboard(this@MainActivity)
                false
            } else {
                hasFocus
            }
        }
    }

    private fun viewUpdateActorInit(){
        binding.run {
            vm.viewUpdateActor = activityLifecycleScope.actor {
                for (a in channel) {
                    vm.run {
                        tvTotal.text = total
                        llSearchCount.visibility = View.VISIBLE

                        tvPage.text = page

                        bntNextPage.visibility = if (vm.nextPageFlag) View.VISIBLE else View.GONE
                        bntBeforePage.visibility = if (vm.page == "1") View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }

    private fun loadingActorInit(){
        vm.loadingActor = activityLifecycleScope.actor(Dispatchers.Main) {
            for (flag in channel) {
                if (flag) {
                    Util.hideKeyboard(this@MainActivity)
                    showLoading(this@MainActivity)
                } else {
                    dismissLoading()
                }
            }
        }
    }

    private fun dragActorInit(){
        vm.dragActor = activityLifecycleScope.actor(Dispatchers.Main) {
            for(a in channel){
                binding.rvImageList.smoothScrollToPosition(0)
            }
        }
    }

    private fun listInit(){
        binding.rvImageList.adapter = vm.imageAdapter
    }

    private fun bntClickListenersInit(){
        binding.run {

            bntBeforePage.intervalClick({
                vm.pageMoveClick(false) })

            bntNextPage.intervalClick({
                vm.pageMoveClick(true) })

            bntTop.intervalClick(vm::topClick)
        }
    }
}
