package com.jjsh.smartshopping.presentation.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentHomeBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.ProductAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.start
import com.jjsh.smartshopping.presentation.ui.product.ProductDetailActivity
import com.jjsh.smartshopping.presentation.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private val productAdapter by lazy {
        ProductAdapter {
            ProductDetailActivity.showDetail(requireContext(), it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        observeData()
    }

    override fun onResume() {
        viewModel.initProducts()
        super.onResume()
    }

    private fun initView() {
        binding.rvProducts.adapter = productAdapter
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.itemAnimator = null
        binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                Timber.e("lastItemPosition : $lastVisibleItemPosition")
                val itemTotalCount = productAdapter.itemCount
                if (lastVisibleItemPosition + 1 == itemTotalCount) {
                    viewModel.getNextPage()
                }
            }
        })
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.products) {
            when (it) {
                is UiState.Success -> {
                    Timber.e("list size : ${it.data.size}")
                    productAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {}
            }
        }
        observeFlowWithLifecycle(viewModel.moveToSearchEvent) {
            if (it) {
                requireContext().start<SearchActivity>()
                viewModel.initMoveToSearchPageEvent()
            }
        }
    }
}