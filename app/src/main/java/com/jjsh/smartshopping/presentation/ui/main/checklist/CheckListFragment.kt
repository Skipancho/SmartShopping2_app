package com.jjsh.smartshopping.presentation.ui.main.checklist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentCheckListBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.adapter.CheckListAdapter
import com.jjsh.smartshopping.presentation.base.BaseFragment
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import com.jjsh.smartshopping.presentation.extension.errorHandling
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckListFragment : BaseFragment<FragmentCheckListBinding>(R.layout.fragment_check_list) {

    private val viewModel by viewModels<CheckListViewModel>()
    private val checkListAdapter by lazy {
        CheckListAdapter(
            _updateCheckItem = { item ->
                if (item.amount > 0)
                    viewModel.updateCheckItem(item)
            },
            deleteCheckItem = { item ->
                AlertDialog.Builder(requireContext())
                    .setMessage("삭제 하시겠습니까?")
                    .setPositiveButton("확인") { _, _ -> viewModel.deleteCheckItem(item) }
                    .setNegativeButton("취소") { d , _ -> d.dismiss() }
                    .show()
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        initView()
        observeData()

        viewModel.getCheckList()
    }

    private fun initView() {
        with(binding.rvCheckList) {
            adapter = checkListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalItemDecoration(bottom = 16.dpToPx()))
        }
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.checkList) {
            when (it) {
                is UiState.Success -> {
                    checkListAdapter.submitList(it.data.toMutableList())
                }
                is UiState.Error -> {
                    requireContext().errorHandling(it.err)
                }
                else -> {

                }
            }
        }
    }

}