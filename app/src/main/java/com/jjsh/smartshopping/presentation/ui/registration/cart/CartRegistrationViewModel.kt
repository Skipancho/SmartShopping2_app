package com.jjsh.smartshopping.presentation.ui.registration.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.model.toCartItem
import com.jjsh.smartshopping.domain.repository.CartItemRepository
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.presentation.UiEvent
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartRegistrationViewModel @Inject constructor(
    private val cartItemRepository: CartItemRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _currentBarcode = MutableStateFlow<Long>(0L)
    val currentBarcode : StateFlow<Long> get() = _currentBarcode

    private val _currentProduct = MutableStateFlow<UiState<Product>>(UiState.Init)
    val currentProduct : StateFlow<UiState<Product>> get() = _currentProduct

    private val _addCartItemEvent = MutableSharedFlow<UiEvent<String>>()
    val addCartItemEvent : SharedFlow<UiEvent<String>> get() = _addCartItemEvent

    private val _productToShow = MutableLiveData<Product>(Product.nullProduct)
    val productToShow : LiveData<Product> get() = _productToShow

    fun setCurrentBarcode(barcodeResult : String){
        runCatching {
            _currentBarcode.value = barcodeResult.toLong()
        }.onFailure {
            Timber.e(it.message)
        }
    }

    private fun initCurrentBarcode(){
        _currentBarcode.value = 0L
        _productToShow.value = Product.nullProduct
    }

    fun findProductByBarcode(barcode : Long){
        viewModelScope.launch {
            productRepository.findProductByBarcode(barcode)
                .onSuccess {
                    _currentProduct.value = UiState.Success(it)
                    _productToShow.value = it

                }.onFailure {
                    _currentProduct.value = UiState.Error(it)
                }
        }
    }

    fun addProductToCart() {
        val product = productToShow.value ?: return
        if (product == Product.nullProduct) return
        viewModelScope.launch {
            cartItemRepository.insertCartItem(product.toCartItem(1))
                .onSuccess {
                    _addCartItemEvent.emit(UiEvent.Success(product.name))
                    initCurrentBarcode()
                }.onFailure {
                    _addCartItemEvent.emit(UiEvent.Error(it))
                }
        }
    }

}