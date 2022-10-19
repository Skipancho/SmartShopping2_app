package com.jjsh.smartshopping.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentHomeBinding
import com.jjsh.smartshopping.presentation.ErrorHandler
import com.jjsh.smartshopping.presentation.UiEvent
import com.jjsh.smartshopping.presentation.adapter.ProductAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private val productAdapter by lazy { ProductAdapter() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.initProducts()

        initView()
        observeData()
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
                if (lastVisibleItemPosition+1 == itemTotalCount) {
                    viewModel.getNextPage()
                }
            }
        })
    }

    private fun observeData() {
        val errorHandler = ErrorHandler(requireContext())
        observeFlowWithLifecycle(viewModel.products) {
            when (it) {
                is UiEvent.Success -> {
                    Timber.e("list size : ${it.data.size}")
                    productAdapter.submitList(it.data.toMutableList())
                }
                is UiEvent.Error -> {
                    errorHandler.errorHandling(it.err)
                }
            }
        }
    }
}