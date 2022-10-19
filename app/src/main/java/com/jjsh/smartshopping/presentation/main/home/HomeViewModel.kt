package com.jjsh.smartshopping.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :ViewModel() {

    private val currentProductList = mutableListOf<Product>()

    private val _products  = MutableStateFlow<UiState<List<Product>>>(UiState.Init)
    val products: StateFlow<UiState<List<Product>>> get() = _products

    private val _isProgressOn = MutableStateFlow(false)
    val isProgressOn: StateFlow<Boolean> get() = _isProgressOn


    init {
        getProducts()
    }

    private fun getProducts(){
        viewModelScope.launch {
            productRepository.getProducts(Long.MAX_VALUE,null,"next",null)
                .onSuccess {
                    currentProductList.addAll(it)
                    _products.value = UiState.Success(currentProductList)
                }.onFailure {
                    _products.value = UiState.Error(it)
                }
        }
    }

    fun getNextPage(){
        if (currentProductList.isEmpty()) return
        viewModelScope.launch {
            _isProgressOn.value = true
            productRepository.getProducts(currentProductList.last().id,null,"next",null)
                .onSuccess {
                    currentProductList.addAll(it)
                    _products.value = UiState.Success(currentProductList)
                }.onFailure {
                    _products.value = UiState.Error(it)
                }
        }
    }

    fun initProducts(){
        _products.value = UiState.Init
        _isProgressOn.value = false
    }
}