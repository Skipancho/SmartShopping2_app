package com.jjsh.smartshopping.presentation.ui.mypage.purchase

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityPurchaseRecordBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.PurchaseRecordAdapter
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.ui.registration.review.ReviewRegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchaseRecordActivity : BaseActivity<ActivityPurchaseRecordBinding>(R.layout.activity_purchase_record) {

    private val viewModel by viewModels<PurchaseRecordViewModel>()

    private val purchaseAdapter by lazy {
        PurchaseRecordAdapter {
            if (!it.isReviewed)
                ReviewRegistrationActivity.startReviewWritePage(this, it.id, it.productId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initActionBar()
        initView()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPurchaseRecord()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.mtbToolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_24)
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

    private fun initView() {
        with(binding.rvPurchaseRecord) {
            adapter = purchaseAdapter
            layoutManager = LinearLayoutManager(this@PurchaseRecordActivity)
            itemAnimator = null
            addItemDecoration(VerticalItemDecoration(top = 2.dpToPx(), bottom = 2.dpToPx()))
        }
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.purchaseRecord) {
            when (it) {
                is UiState.Success -> {
                    purchaseAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    errorHandling(it.err)
                }
                else -> {}
            }
        }
    }
}