package com.jjsh.smartshopping.presentation.ui.registration.review

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityReviewRegistrationBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.start
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ReviewRegistrationActivity :
    BaseActivity<ActivityReviewRegistrationBinding>(R.layout.activity_review_registration) {

    private var _reviewId = -1L
    private val reviewId : Long get() = _reviewId

    private var purchaseId: Long by Delegates.notNull<Long>()
    private var productId: Long by Delegates.notNull<Long>()

    private val viewModel by viewModels<ReviewRegistrationViewModel>()

    private var reviewScoreFragment: ReviewScoreFragment? = null
    private var reviewTextFragment: ReviewTextFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        getIdsFromIntent()
        initActionBar()
        observeData()
    }

    private fun getIdsFromIntent() {
        _reviewId = intent.getLongExtra(REVIEW_ID, -1L)

        if (reviewId == -1L) {
            purchaseId = intent.getLongExtra(PURCHASE_ID, -1L)
            productId = intent.getLongExtra(PRODUCT_ID, -1L)

            if (productId == -1L || purchaseId == -1L) {
                finish()
            }
            viewModel.getProduct(productId, purchaseId)
        }else {
            viewModel.getReview(reviewId)
        }
    }

    private fun showScoreFragment() {
        if (reviewScoreFragment == null) {
            reviewScoreFragment = ReviewScoreFragment()
            supportFragmentManager.commit {
                add(binding.layoutFragment.id, reviewScoreFragment!!)
            }
        }
        supportFragmentManager.commit {
            reviewScoreFragment?.let { show(it) }
            reviewTextFragment?.let { hide(it) }
        }

        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun showTextFragment() {
        if (reviewTextFragment == null) {
            reviewTextFragment = ReviewTextFragment()
            supportFragmentManager.commit {
                add(binding.layoutFragment.id, reviewTextFragment!!)
            }
        }
        supportFragmentManager.commit {
            reviewScoreFragment?.let { hide(it) }
            reviewTextFragment?.let { show(it) }
        }
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
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.showScoreFragment) {
            when (it) {
                true -> showScoreFragment()
                false -> showTextFragment()
            }
        }

        observeFlowWithLifecycle(viewModel.uiState) {
            when (it) {
                is UiState.Success -> finish()
                is UiState.Error -> errorHandling(it.err)
                else -> {}
            }
        }
    }

    companion object {
        const val PURCHASE_ID = "purchase_id"
        const val PRODUCT_ID = "product_id"
        const val REVIEW_ID = "review_id"
        fun startReviewWritePage(context: Context, purchaseId: Long, productId: Long) {
            context.start<ReviewRegistrationActivity> {
                it.putExtra(PURCHASE_ID, purchaseId)
                it.putExtra(PRODUCT_ID, productId)
            }
        }

        fun startReviewEditPage(context: Context, reviewId: Long) {
            context.start<ReviewRegistrationActivity> {
                it.putExtra(REVIEW_ID,reviewId)
            }
        }
    }
}