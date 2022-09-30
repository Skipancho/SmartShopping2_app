package com.jjsh.smartshopping.presentation.signin

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySigninBinding
import com.jjsh.smartshopping.presentation.ErrorHandler
import com.jjsh.smartshopping.presentation.MainActivity
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.clearTaskAndStart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SigninActivity : BaseActivity<ActivitySigninBinding>() {

    override val layoutRes: Int
        get() = R.layout.activity_signin

    private val viewModel by viewModels<SigninViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        observeData()
    }

    private fun observeData() {
        val errorHandler = ErrorHandler(this)
        viewModel.uiState.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is UiState.Loading -> {
                        viewModel.setProgress(true)
                    }
                    is UiState.Success -> {
                        viewModel.setProgress(false)
                        clearTaskAndStart<MainActivity>()
                    }
                    is UiState.Error -> {
                        viewModel.setProgress(false)
                        errorHandler.errorHandling(it.err)
                    }
                    else -> {
                        //do nothing
                    }
                }
            }.launchIn(lifecycleScope)
    }
}