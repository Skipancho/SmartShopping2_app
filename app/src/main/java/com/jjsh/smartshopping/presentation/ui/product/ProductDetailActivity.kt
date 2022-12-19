package com.jjsh.smartshopping.presentation.ui.product

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityProductDetailBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.ui.registration.checklist.ChecklistRegistrationDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity :
    BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {

    private val viewModel by viewModels<ProductDetailViewModel>()

    private var _productId : Long = -1L
    private val productId get() = _productId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        _productId = intent.getLongExtra(PRODUCT_ID, -1L)

        initActionBar()
        initView()
        initData()
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

    private fun initView() {
        binding.btnAddToChecklist.setOnClickListener {
            ChecklistRegistrationDialog
                .newInstance(productId)
                .show(supportFragmentManager,null)
        }
    }

    private fun initData() {
        viewModel.getProduct(productId)
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.uiState) {
            when (it) {
                is UiState.Error -> {
                    errorHandling(it.err)
                }
                else -> {}
            }
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

    companion object {
        private const val PRODUCT_ID = "PRODUCT_ID"
        fun showDetail(context: Context, productId: Long) {
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_ID, productId)
            }
            context.startActivity(intent)
        }
    }
}