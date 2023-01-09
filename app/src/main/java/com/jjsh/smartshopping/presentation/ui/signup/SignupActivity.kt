package com.jjsh.smartshopping.presentation.ui.signup

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySignupBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.shortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {

    private val viewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initActionBar()
        observeData()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.mtbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24)
    }

    private fun observeData() {
        with(viewModel) {
            observeFlowWithLifecycle(isPasswordValidated()) {
                binding.tilPasswordCheck.error =
                    if (it) null else getString(R.string.err_msg_password)
            }
            observeFlowWithLifecycle(userId) {
                setIdCheck(false)
            }
            observeFlowWithLifecycle(nickName) {
                setNickNameCheck(false)
            }
            observeFlowWithLifecycle(idCheckEvent) {
                when (it) {
                    is UiState.Success -> {
                        shortToast(getString(R.string.toast_msg_id_checked))
                    }
                    is UiState.Error -> {
                        errorHandling(it.err)
                    }
                    else -> {}
                }
                viewModel.initUiState()
            }
            observeFlowWithLifecycle(nickNameCheckEvent) {
                when (it) {
                    is UiState.Success -> {
                        shortToast(getString(R.string.toast_msg_nickname_checked))
                    }
                    is UiState.Error -> {
                        errorHandling(it.err)
                    }
                    else -> {}
                }
                viewModel.initUiState()
            }
            observeFlowWithLifecycle(uiState) {
                when (it) {
                    is UiState.Init -> {
                        viewModel.setProgress(false)
                    }
                    is UiState.Loading -> {
                        viewModel.setProgress(true)
                    }
                    is UiState.Success -> {
                        viewModel.setProgress(false)
                        shortToast(getString(R.string.toast_msg_success_signup))
                        finish()
                    }
                    is UiState.Error -> {
                        viewModel.setProgress(false)
                        errorHandling(it.err)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}