package com.jjsh.smartshopping.presentation.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.model.Review
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.domain.repository.ReviewRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _productState = MutableStateFlow<UiState<Product>>(UiState.Init)
    val productState: StateFlow<UiState<Product>> get() = _productState

    private val _productReviews = MutableStateFlow<UiState<List<Review>>>(UiState.Init)
    val productReviews: StateFlow<UiState<List<Review>>> get() = _productReviews

    private val _currentProduct = MutableStateFlow(Product.nullProduct)
    val currentProduct: StateFlow<Product> get() = _currentProduct


    fun getProduct(productId: Long) {
        viewModelScope.launch {
            productRepository.getProductItem(productId)
                .onSuccess {
                    _productState.value = UiState.Success(it)
                    _currentProduct.value = it
                }.onFailure {
                    _productState.value = UiState.Error(it)
                }
        }
    }

    fun getReviews(productId: Long) {
        viewModelScope.launch {
            reviewRepository.getReviews(productId)
                .onSuccess {
                    val list = it.toMutableList()
                    while (list.size > 3) list.removeLast()
                    _productReviews.value = UiState.Success(list)
                }.onFailure {
                    _productReviews.value = UiState.Error(it)
                }
        }
    }
}