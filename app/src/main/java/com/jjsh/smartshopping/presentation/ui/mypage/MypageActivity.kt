package com.jjsh.smartshopping.presentation.ui.mypage

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityMypageBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.clearTaskAndStart
import com.jjsh.smartshopping.presentation.extension.start
import com.jjsh.smartshopping.presentation.ui.mypage.purchase.PurchaseRecordActivity
import com.jjsh.smartshopping.presentation.ui.mypage.review.ReviewManagementActivity
import com.jjsh.smartshopping.presentation.ui.mypage.withdrawal.WithdrawalActivity
import com.jjsh.smartshopping.presentation.ui.signin.SigninActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageActivity : BaseActivity<ActivityMypageBinding>(R.layout.activity_mypage) {

    private val viewModel by viewModels<MypageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initActionBar()
        observeData()
        setEvent()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.mtbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24)
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.signOutEvent) {
            when (it) {
                is UiState.Success -> {
                    clearTaskAndStart<SigninActivity>()
                }
                else -> {}
            }
        }
    }

    private fun setEvent() {
        binding.btnPurchaseRecord.setOnClickListener {
            start<PurchaseRecordActivity>()
        }
        binding.btnManageReview.setOnClickListener {
            start<ReviewManagementActivity>()
        }
        binding.btnWithdrawal.setOnClickListener {
            start<WithdrawalActivity>()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_default, R.anim.anim_right_out)
    }
}