package com.jjsh.smartshopping.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.model.SearchHistory
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.domain.repository.SearchHistoryRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {
    private var currentProductList = listOf<Product>()

    private val _currentSearchText = MutableStateFlow("")
    val currentSearchText: StateFlow<String> get() = _currentSearchText

    private val _searchResult = MutableStateFlow<UiState<List<Product>>>(UiState.Init)
    val searchResult: StateFlow<UiState<List<Product>>> get() = _searchResult

    private val _hasSearchViewFocus = MutableStateFlow(true)
    val hasSearchViewFocus: StateFlow<Boolean> get() = _hasSearchViewFocus

    private val _searchHistory = MutableStateFlow<UiState<List<SearchHistory>>>(UiState.Init)
    val searchHistory: StateFlow<UiState<List<SearchHistory>>> get() = _searchHistory

    private var _moveToBack: () -> Unit = { }
    val moveToBack: () -> Unit get() = _moveToBack

    private var debounceJob: Job? = null

    init {
        getSearchHistory()
    }

    val search: (String) -> Unit = {
        clearProductList()
        searchProducts(Long.MAX_VALUE, it)
    }

    val onTextClear: () -> Unit = {
        _searchResult.value = UiState.Success(listOf())
    }

    val onTextChange: (String) -> Unit = {
        debounceJob?.cancel()
        if (it.isNotEmpty()) {
            debounceSearch(it)
        }
    }

    val onFocusSearch: (Boolean) -> Unit = {
        _hasSearchViewFocus.value = it
    }

    fun getNextPage() {
        if (currentProductList.isEmpty()) return
        searchProducts(currentProductList.last().id, currentSearchText.value)
    }

    fun searchProducts(startProductId: Long, keyword: String) {
        setSearchText(keyword)
        viewModelScope.launch {
            productRepository.getProducts(productId = startProductId, keyword = keyword)
                .onSuccess {
                    currentProductList = currentProductList + it
                    _searchResult.value = UiState.Success(currentProductList)
                    insertSearchHistory(keyword)
                    _hasSearchViewFocus.value = false
                }.onFailure {
                    _searchResult.value = UiState.Error(it)
                }
        }
    }

    fun deleteSearchHistory(history: SearchHistory) {
        viewModelScope.launch {
            searchHistoryRepository.deleteSearchHistory(history)
        }
    }

    fun setSearchText(text: String) {
        _currentSearchText.value = text
    }

    fun clearProductList() {
        currentProductList = listOf()
    }

    fun setMoveToBack(action: () -> Unit) {
        _moveToBack = action
    }

    private fun debounceSearch(keyword: String) {
        debounceJob = viewModelScope.launch {
            delay(1000L)
            clearProductList()
            searchProducts(Long.MAX_VALUE, keyword)
            _hasSearchViewFocus.value = false
        }
    }

    private fun insertSearchHistory(keyword: String) {
        if (keyword.isEmpty()) return
        viewModelScope.launch {
            searchHistoryRepository.insertSearchHistory(
                SearchHistory(keyword)
            ).onSuccess {
                Timber.d("검색어 기록 성공")
            }.onFailure {
                Timber.e("검색어 기록 실패")
            }
        }
    }

    private fun getSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.getSearchHistory().collectLatest { result ->
                result.onSuccess {
                    _searchHistory.value = UiState.Success(it)
                }.onFailure {
                    _searchHistory.value = UiState.Error(it)
                }
            }
        }
    }
}