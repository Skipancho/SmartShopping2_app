package com.jjsh.smartshopping.presentation.ui.mypage.review

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityReviewManagementBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.ReviewAdapter
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.ui.registration.review.ReviewRegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewManagementActivity
    : BaseActivity<ActivityReviewManagementBinding>(R.layout.activity_review_management) {

    private val viewModel by viewModels<ReviewManagementViewModel>()

    private val reviewAdapter by lazy {
        ReviewAdapter ({
            ReviewRegistrationActivity.startReviewEditPage(this, it.id)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActionBar()
        initView()
        observeData()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getReviews()
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
        //val dividerDecoration = CustomDividerDecoration(2.dpToPx(),10.dpToPx(),R.color.gray_828282)
        val itemDecoration =
            VerticalItemDecoration(top = 8.dpToPx(), bottom = 4.dpToPx(), width = 24.dpToPx())
        with(binding.rvReviews) {
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(this@ReviewManagementActivity)
            itemAnimator = null
            //addItemDecoration(dividerDecoration)
            addItemDecoration(itemDecoration)
        }
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.reviews) {
            when (it) {
                is UiState.Success -> {
                    reviewAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    errorHandling(it.err)
                }
                else -> {}
            }
        }
    }
}