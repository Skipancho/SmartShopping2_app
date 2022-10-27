package com.jjsh.smartshopping.presentation.search

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySearchBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.search.history.SearchHistoryFragment
import com.jjsh.smartshopping.presentation.search.result.SearchResultFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private val historyFragment by lazy { SearchHistoryFragment() }
    private val resultFragment by lazy { SearchResultFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        viewModel.setMoveToBack { finish() }
        observeData()
    }

    private fun observeData() {
        val inputMethodManager = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        observeFlowWithLifecycle(viewModel.hasSearchViewFocus) {
            if (it) {
                supportFragmentManager.commit {
                    replace(binding.flFragment.id, historyFragment)
                }
            } else {
                supportFragmentManager.commit {
                    replace(binding.flFragment.id, resultFragment)
                }
                if (binding.stvSearch.findFocus() != null)
                    binding.stvSearch.findFocus().clearFocus()
                inputMethodManager.hideSoftInputFromWindow(binding.stvSearch.windowToken, 0)
            }
        }

        observeFlowWithLifecycle(viewModel.currentSearchText){
            binding.stvSearch.setText("")
        }
    }
}