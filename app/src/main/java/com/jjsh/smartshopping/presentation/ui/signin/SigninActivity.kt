package com.jjsh.smartshopping.presentation.ui.signin

import android.os.Bundle
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySigninBinding
import com.jjsh.smartshopping.presentation.ui.main.MainActivity
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.clearTaskAndStart
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.start
import com.jjsh.smartshopping.presentation.ui.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninActivity : BaseActivity<ActivitySigninBinding>(R.layout.activity_signin) {

    private val viewModel by viewModels<SigninViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        observeData()
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.uiState){
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
                    errorHandling(it.err)
                }
                else -> {
                    //do nothing
                }
            }
        }
        observeFlowWithLifecycle(viewModel.moveToSignupEvent){
            if (it){
                start<SignupActivity>()
                viewModel.initEvent()
            }
        }
    }
}