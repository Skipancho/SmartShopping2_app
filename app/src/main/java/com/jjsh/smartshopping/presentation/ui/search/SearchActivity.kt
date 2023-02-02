package com.jjsh.smartshopping.presentation.ui.search

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySearchBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.ui.search.history.SearchHistoryFragment
import com.jjsh.smartshopping.presentation.ui.search.result.SearchResultFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private val viewModel by viewModels<SearchViewModel>()
    private var historyFragment: SearchHistoryFragment? = null
    private var resultFragment: SearchResultFragment? = null

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
                showHistory()
            } else {
                showResult()
                if (binding.stvSearch.findFocus() != null)
                    binding.stvSearch.findFocus().clearFocus()
                inputMethodManager.hideSoftInputFromWindow(binding.stvSearch.windowToken, 0)
            }
        }

        observeFlowWithLifecycle(viewModel.currentSearchText) {
            binding.stvSearch.setText("")
        }
    }

    private fun showHistory() {
        if (historyFragment == null){
            historyFragment = SearchHistoryFragment()
            supportFragmentManager.commit {
                add(binding.flFragment.id,historyFragment!!)
            }
        }
        supportFragmentManager.commit {
            historyFragment?.let { show(it) }
            resultFragment?.let { hide(it) }
        }
    }

    private fun showResult() {
        if (resultFragment == null){
            resultFragment = SearchResultFragment()
            supportFragmentManager.commit {
                add(binding.flFragment.id,resultFragment!!)
            }
        }
        supportFragmentManager.commit {
            historyFragment?.let { hide(it) }
            resultFragment?.let { show(it) }
        }
    }
}