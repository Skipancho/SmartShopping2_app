package com.jjsh.smartshopping.presentation.ui.registration.cart

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityCartRegistrationBinding
import com.jjsh.smartshopping.presentation.UiEvent
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.shortToast
import com.journeyapps.barcodescanner.CaptureManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartRegistrationActivity :
    BaseActivity<ActivityCartRegistrationBinding>(R.layout.activity_cart_registration) {

    private val viewModel by viewModels<CartRegistrationViewModel>()

    private lateinit var captureManager: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initActionBar()
        initBarcodeScanner(savedInstanceState)
        observeData()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.mtbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_24)
    }

    private fun initBarcodeScanner(savedInstanceState: Bundle?) {
        captureManager = CaptureManager(this, binding.bvBarcodeScanner)
        captureManager.initializeFromIntent(intent, savedInstanceState)
        binding.bvBarcodeScanner.decodeContinuous {
            viewModel.setCurrentBarcode(it.text)
        }
    }

    private fun observeData() {
        observeBarcode()
        observeCurrentProduct()
        observeAddCartEvent()
    }

    private fun observeBarcode() {
        observeFlowWithLifecycle(viewModel.currentBarcode) {
            if (it > 0) {
                viewModel.findProductByBarcode(it)
            }
        }
    }

    private fun observeCurrentProduct() {
        observeFlowWithLifecycle(viewModel.currentProduct) {
            when (it) {
                is UiState.Error -> {
                    errorHandling(it.err)
                }
                else -> {

                }
            }
        }
    }

    private fun observeAddCartEvent() {
        observeFlowWithLifecycle(viewModel.addCartItemEvent) {
            when(it) {
                is UiEvent.Success -> {
                    shortToast("${it.data}을 장바구니에 추가했습니다.")
                }
                is UiEvent.Error -> {
                    errorHandling(it.err)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        captureManager.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}