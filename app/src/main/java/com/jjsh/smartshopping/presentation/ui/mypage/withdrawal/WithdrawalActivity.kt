package com.jjsh.smartshopping.presentation.ui.mypage.withdrawal

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityWithdrawalBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.clearTaskAndStart
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.shortToast
import com.jjsh.smartshopping.presentation.ui.signin.SigninActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalActivity : BaseActivity<ActivityWithdrawalBinding>(R.layout.activity_withdrawal) {

    private val viewModel by viewModels<WithdrawalViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initActionBar()
        observeData()
    }

    private fun initActionBar(){
        setSupportActionBar(binding.mtbToolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_24)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.withdrawalEvent){
            when(it){
                is UiState.Success -> {
                    shortToast(getString(R.string.toast_msg_success_withdrawal))
                    clearTaskAndStart<SigninActivity>()
                }
                is UiState.Error -> {
                    errorHandling(it.err)
                }
                else -> {}
            }
        }
    }
}