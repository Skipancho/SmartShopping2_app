package com.jjsh.smartshopping.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.presentation.UiEvent
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :ViewModel() {

    private val currentProductList = mutableListOf<Product>()

    private val _products  = MutableSharedFlow<UiEvent<List<Product>>>()
    val products: SharedFlow<UiEvent<List<Product>>> get() = _products

    private val _isProgressOn = MutableStateFlow(false)
    val isProgressOn: StateFlow<Boolean> get() = _isProgressOn

    private fun getProducts(startProductId: Long){
        viewModelScope.launch {
            _isProgressOn.value = true
            productRepository.getProducts(startProductId,null,"next",null)
                .onSuccess {
                    currentProductList.addAll(it)
                    _products.emit(UiEvent.Success(currentProductList))
                }.onFailure {
                    _products.emit(UiEvent.Error(it))
                }
            _isProgressOn.value = false
        }
    }

    fun initProducts(){
        currentProductList.clear()
        getProducts(Long.MAX_VALUE)
    }

    fun getNextPage(){
        if (currentProductList.isEmpty()) return
        getProducts(currentProductList.last().id)
    }
}