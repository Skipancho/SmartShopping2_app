package com.jjsh.smartshopping.presentation.signup

import android.os.Bundle
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySignupBinding
import com.jjsh.smartshopping.presentation.ErrorHandler
import com.jjsh.smartshopping.presentation.UiEvent
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_signup

    private val viewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        observeData()
    }

    private fun observeData() {
        val errorHandler = ErrorHandler(this)
        with(viewModel) {
            observeFlowWithLifecycle(isPasswordValidated()){
                binding.tilPasswordCheck.error =
                    if (it) null else getString(R.string.err_msg_password)
            }
            observeFlowWithLifecycle(userId){
                setIdCheck(false)
            }
            observeFlowWithLifecycle(nickName){
                setNickNameCheck(false)
            }
            observeFlowWithLifecycle(idCheckEvent){
                when(it){
                    is UiEvent.Success -> {
                        shortToast(getString(R.string.toast_msg_id_checked))
                    }
                    is UiEvent.Error -> {
                        errorHandler.errorHandling(it.err)
                    }
                }
                viewModel.initUiState()
            }
            observeFlowWithLifecycle(nickNameCheckEvent){
                when(it){
                    is UiEvent.Success -> {
                        shortToast(getString(R.string.toast_msg_nickname_checked))
                    }
                    is UiEvent.Error -> {
                        errorHandler.errorHandling(it.err)
                    }
                }
                viewModel.initUiState()
            }
            observeFlowWithLifecycle(uiState){
                when(it){
                    is UiState.Init -> {

                    }
                    is UiState.Loading -> {

                    }
                    is UiState.Success -> {
                        shortToast(getString(R.string.toast_msg_success_signup))
                        finish()
                    }
                    is UiState.Error -> {
                        errorHandler.errorHandling(it.err)
                    }
                }
            }
        }
    }
}