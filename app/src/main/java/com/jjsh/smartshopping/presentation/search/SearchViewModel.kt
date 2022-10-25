package com.jjsh.smartshopping.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private var currentProductList = listOf<Product>()
    private var currentSearchText: String = ""

    private val _searchResult = MutableStateFlow<UiState<List<Product>>>(UiState.Init)
    val searchResult: StateFlow<UiState<List<Product>>> get() = _searchResult

    private val _hasSearchViewFocus = MutableStateFlow(true)
    val hasSearchViewFocus: StateFlow<Boolean> get() = _hasSearchViewFocus

    private var _moveToBack: () -> Unit = { }
    val moveToBack: () -> Unit get() = _moveToBack

    private var debounceJob : Job? = null

    val search: (String) -> Unit = {
        currentProductList = listOf()
        currentSearchText = it
        searchProducts(Long.MAX_VALUE)
    }

    val delete: () -> Unit = {
        _searchResult.value = UiState.Success(listOf())
    }

    val onTextChange: (String) -> Unit = {
        debounceJob?.cancel()
        if (it.isNotEmpty()){
            currentSearchText = it
            debounceSearch()
        }
    }

    val onFocusSearch: (Boolean) -> Unit = {
        _hasSearchViewFocus.value = it
    }

    fun getNextPage() {
        if (currentProductList.isEmpty()) return
        searchProducts(currentProductList.last().id)
    }

    private fun debounceSearch(){
        debounceJob = viewModelScope.launch {
            delay(1000L)
            currentProductList = listOf()
            searchProducts(Long.MAX_VALUE)
            _hasSearchViewFocus.value = false
        }
    }

    private fun searchProducts(startProductId: Long) {
        viewModelScope.launch {
            productRepository.getProducts(productId = startProductId, keyword = currentSearchText)
                .onSuccess {
                    currentProductList += it
                    _searchResult.value = UiState.Success(currentProductList)
                }.onFailure {
                    _searchResult.value = UiState.Error(it)
                }
        }
    }

    fun setMoveToBack(action: () -> Unit) {
        _moveToBack = action
    }
}