package com.jjsh.smartshopping.presentation.ui.search.result

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentSearchResultBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.ProductAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.ui.search.SearchViewModel

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(R.layout.fragment_search_result) {

    private val viewModel by activityViewModels<SearchViewModel>()
    private val productAdapter by lazy { ProductAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.rvSearchResult.adapter = productAdapter
        binding.rvSearchResult.layoutManager = GridLayoutManager(requireContext(),2)
        binding.rvSearchResult.itemAnimator = null
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.searchResult){
            when(it){
                is UiState.Success -> {
                    productAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {}
            }
        }
    }
}