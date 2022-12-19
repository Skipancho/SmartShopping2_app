package com.jjsh.smartshopping.presentation.ui.product

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
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Product>>(UiState.Init)
    val uiState: StateFlow<UiState<Product>> get() = _uiState

    private val _currentProduct = MutableStateFlow(Product.nullProduct)
    val currentProduct: StateFlow<Product> get() = _currentProduct


    fun getProduct(productId: Long) {
        viewModelScope.launch {
            productRepository.getProductItem(productId)
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                    _currentProduct.value = it
                }.onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }
}