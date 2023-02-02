package com.jjsh.smartshopping.presentation.ui.product

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityProductDetailBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.ImageViewPagerAdapter
import com.jjsh.smartshopping.presentation.adapter.ReviewAdapter
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.start
import com.jjsh.smartshopping.presentation.ui.product.review.ReviewActivity
import com.jjsh.smartshopping.presentation.ui.registration.checklist.ChecklistRegistrationDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {

    private val viewModel by viewModels<ProductDetailViewModel>()

    private val imageViewPagerAdapter by lazy {
        ImageViewPagerAdapter()
    }
    private val reviewAdapter by lazy {
        ReviewAdapter(isUserReview = false)
    }

    private var _productId: Long = -1L
    private val productId get() = _productId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initData()
        initActionBar()
        initView()
        observeData()
        setEvent()
    }

    private fun initData() {
        _productId = intent.getLongExtra(PRODUCT_ID, -1L)
        viewModel.getProduct(productId)
        viewModel.getReviews(productId)
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
        binding.btnAddToChecklist.setOnClickListener {
            ChecklistRegistrationDialog
                .newInstance(productId)
                .show(supportFragmentManager, null)
        }

        binding.vpDetailImage.adapter = imageViewPagerAdapter

        TabLayoutMediator(binding.tlImageIndicator, binding.vpDetailImage) { _, _ -> }.attach()

        with(binding.rvReviewsPreview) {
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

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.productState) {
            when (it) {
                is UiState.Success -> {
                    imageViewPagerAdapter.setImageUrls(it.data.imagePaths)
                }
                is UiState.Error -> {
                    errorHandling(it.err)
                }
                else -> {}
            }
        }

        observeFlowWithLifecycle(viewModel.productReviews) {
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

    private fun setEvent() {
        binding.tvReviewTitle.setOnClickListener {
            ReviewActivity.startReviewPage(this, productId)
        }
        binding.btnMoreReview.setOnClickListener {
            ReviewActivity.startReviewPage(this, productId)
        }
    }

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"
        fun showDetail(context: Context, productId: Long) {
            context.start<ProductDetailActivity> {
                it.putExtra(PRODUCT_ID, productId)
            }
        }
    }
}