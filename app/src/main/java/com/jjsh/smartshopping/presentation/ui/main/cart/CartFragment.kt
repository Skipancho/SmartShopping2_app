package com.jjsh.smartshopping.presentation.ui.main.cart

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentCartBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.CartAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.start
import com.jjsh.smartshopping.presentation.ui.registration.cart.CartRegistrationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart) {

    private val viewModel by viewModels<CartViewModel>()
    private val cartAdapter by lazy {
        CartAdapter(
            _updateCartItem = { item ->
                if (item.amount > 0)
                    viewModel.updateCartItem(item)

            },
            deleteCartItem = { item ->
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.text_do_you_wanna_delete))
                    .setPositiveButton(getString(R.string.text_yes)) { _, _ ->
                        viewModel.deleteCartItem(
                            item
                        )
                    }
                    .setNegativeButton(getString(R.string.text_no)) { d, _ -> d.dismiss() }
                    .show()
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        observeData()

        viewModel.getCartItems()
    }

    private fun initView() {
        with(binding.rvCart) {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalItemDecoration(bottom = 16.dpToPx()))
        }

        binding.btnAddToCart.setOnClickListener {
            requireContext().start<CartRegistrationActivity>()
        }

        binding.btnPurchaseCompleted.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.text_do_you_wanna_purchase))
                .setPositiveButton(getString(R.string.text_yes)) { _, _ -> viewModel.registerPurchaseRecord() }
                .setNegativeButton(getString(R.string.text_no)) { d, _ -> d.dismiss() }
                .show()
        }
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.cartList) {
            when (it) {
                is UiState.Success -> {
                    cartAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {}
            }
        }

        observeFlowWithLifecycle(viewModel.registerPurchaseEvent) {
            when (it) {
                is UiState.Success -> {
                    viewModel.deleteCartItem(*it.data.toTypedArray())
                    viewModel.initPurchaseEvent()
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {}
            }
        }
    }
}