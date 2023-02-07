package com.jjsh.smartshopping.presentation.ui.search.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentSearchResultBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.ProductAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.ui.product.ProductDetailActivity
import com.jjsh.smartshopping.presentation.ui.search.SearchViewModel

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val viewModel by activityViewModels<SearchViewModel>()

    private val productAdapter by lazy {
        ProductAdapter {
            ProductDetailActivity.showDetail(requireContext(), it)
        }
    }

    private val gridLayoutManager by lazy { GridLayoutManager(requireContext(), 2) }
    private val linearLayoutManager by lazy { LinearLayoutManager(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        initView()
        observeData()
    }

    private fun initView() {
        with(binding.rvSearchResult) {
            adapter = productAdapter
            itemAnimator = null
            layoutManager = if (productAdapter.isGrid) gridLayoutManager else linearLayoutManager
        }
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.searchResult) {
            when (it) {
                is UiState.Success -> {
                    productAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {}
            }
        }

        observeFlowWithLifecycle(viewModel.isLayoutTypeGrid) {
            if (productAdapter.isGrid != it) {
                if (it) {
                    productAdapter.setGrid(true)
                    binding.rvSearchResult.layoutManager = gridLayoutManager
                } else {
                    productAdapter.setGrid(false)
                    binding.rvSearchResult.layoutManager = linearLayoutManager
                }
            }
        }
    }
}