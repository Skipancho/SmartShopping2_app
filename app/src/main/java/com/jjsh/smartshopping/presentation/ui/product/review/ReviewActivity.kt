package com.jjsh.smartshopping.presentation.ui.product.review

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityReviewBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.ReviewAdapter
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import com.jjsh.smartshopping.presentation.extension.start
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ReviewActivity : BaseActivity<ActivityReviewBinding>(R.layout.activity_review) {

    private var productId: Long by Delegates.notNull()

    private val viewModel by viewModels<ReviewViewModel>()

    private val reviewAdapter by lazy {
        ReviewAdapter(isUserReview = false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActionBar()
        initView()
        getData()
        observeData()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.mtbToolbar)
        supportActionBar?.run {
            title = ""
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
        with(binding.rvReviews){
            adapter = reviewAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
            addItemDecoration(
                VerticalItemDecoration(
                    top = 8.dpToPx(),
                    bottom = 4.dpToPx(),
                    width = 24.dpToPx()
                )
            )
        }
    }

    private fun getData() {
        productId = intent.getLongExtra(PRODUCT_ID, -1L)

        if (productId == -1L) {
            finish()
            return
        }

        viewModel.getReviews(productId)
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.reviews) {
            when (it) {
                is UiState.Success -> {
                    if (it.data.isNotEmpty()){
                        reviewAdapter.submitList(it.data)
                    }
                    binding.rvReviews.isVisible = it.data.isNotEmpty()
                    binding.tvEmpty.isVisible = it.data.isEmpty()
                }
                is UiState.Error -> {

                }
                else -> {}
            }
        }
    }

    companion object {
        private const val PRODUCT_ID = "product_id"
        fun startReviewPage(context: Context, productId: Long) {
            context.start<ReviewActivity> {
                it.putExtra(PRODUCT_ID, productId)
            }
        }
    }
}