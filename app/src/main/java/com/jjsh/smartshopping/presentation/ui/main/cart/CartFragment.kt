package com.jjsh.smartshopping.presentation.ui.main.cart

import android.os.Bundle
import android.view.View
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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(R.layout.fragment_cart) {

    private val viewModel by viewModels<CartViewModel>()
    private val cartAdapter by lazy {
        CartAdapter(
            _updateCartItem = {

            },
            _deleteCartItem = {

            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        observeData()
    }

    private fun initView() {
        with(binding.rvCart) {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalItemDecoration(bottom = 16.dpToPx()))
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
    }
}