package com.jjsh.smartshopping.presentation.ui.search.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentSearchHistoryBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.SearchHistoryAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.ui.search.SearchViewModel

class SearchHistoryFragment : BaseFragment<FragmentSearchHistoryBinding>(R.layout.fragment_search_history) {

    private val viewModel by activityViewModels<SearchViewModel>()

    private val historyAdapter by lazy {
        SearchHistoryAdapter(
            onClickItem = {
                viewModel.setSearchText(it)
                viewModel.clearProductList()
                viewModel.searchProducts(Long.MAX_VALUE,it)
            },
            onDeleteBtnClick = {
                viewModel.deleteSearchHistory(it)
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
        with(binding.rvSearchHistory){
            adapter = historyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.searchHistory){
            when(it){
                is UiState.Success -> {
                    historyAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {}
            }
        }
    }
}