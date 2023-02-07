package com.jjsh.smartshopping.presentation.ui.main.chart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentChartBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.ChartAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import com.jjsh.smartshopping.presentation.extension.errorHandling
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartFragment : BaseFragment<FragmentChartBinding>(R.layout.fragment_chart) {

    private val viewModel by viewModels<ChartViewModel>()
    private val chartAdapter by lazy { ChartAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        observeData()
    }

    private fun initView() {
        with(binding.rvChart) {
            adapter = chartAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.chartData) {
            when(it) {
                is UiState.Success -> {
                    chartAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {}
            }
        }
    }

    override fun onResume() {
        viewModel.getChartData()
        super.onResume()
    }
}