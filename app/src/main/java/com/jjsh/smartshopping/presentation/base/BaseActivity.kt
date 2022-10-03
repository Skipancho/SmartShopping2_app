package com.jjsh.smartshopping.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity<T: ViewDataBinding>: AppCompatActivity() {
    protected lateinit var binding: T
    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
    }

    protected fun <T> observeFlowWithLifecycle(flow: Flow<T>, block: (T) -> Unit) {
        flow.flowWithLifecycle(lifecycle)
            .onEach {
                block(it)
            }.launchIn(lifecycleScope)
    }
}