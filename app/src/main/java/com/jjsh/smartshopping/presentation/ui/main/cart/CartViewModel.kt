package com.jjsh.smartshopping.presentation.ui.main.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.domain.model.CheckItem
import com.jjsh.smartshopping.domain.repository.CartItemRepository
import com.jjsh.smartshopping.domain.repository.PurchaseRepository
import com.jjsh.smartshopping.domain.usecase.GetCartItemsUseCase
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val cartItemRepository: CartItemRepository,
    private val purchaseRepository: PurchaseRepository
) : ViewModel() {
    private val _cartList = MutableStateFlow<UiState<List<CartItem>>>(UiState.Init)
    val cartList: StateFlow<UiState<List<CartItem>>> get() = _cartList

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> get() = _totalPrice

    private val _registerPurchaseEvent = MutableStateFlow<UiState<List<CartItem>>>(UiState.Init)
    val registerPurchaseEvent: StateFlow<UiState<List<CartItem>>> get() = _registerPurchaseEvent

    fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collectLatest { result ->
                result.onSuccess {
                    _cartList.value = UiState.Success(it)
                    _totalPrice.value = it.sumOf { item -> item.totalPrice }
                }.onFailure {
                    _cartList.value = UiState.Error(it)
                }
            }
        }
    }

    fun updateCartItem(vararg cartItem: CartItem) {
        viewModelScope.launch {
            cartItemRepository.updateCartItem(*cartItem)
                .onSuccess {
                    Timber.d("update success")
                }.onFailure {
                    Timber.e("update failure")
                }
        }
    }

    fun deleteCartItem(vararg cartItem: CartItem) {
        viewModelScope.launch {
            cartItemRepository.deleteCartItem(*cartItem)
                .onSuccess {
                    Timber.d("delete success")
                }.onFailure {
                    Timber.e("delete failure")
                }
        }
    }

    fun registerPurchaseRecord() {
        val cartListValue = cartList.value
        val cartItems = if (cartListValue is UiState.Success) cartListValue.data else return
        viewModelScope.launch {
            purchaseRepository.registerPurchaseRecord(cartItems)
                .onSuccess {
                    _registerPurchaseEvent.value = UiState.Success(cartItems)
                }.onFailure {
                    _registerPurchaseEvent.value = UiState.Error(it)
                }
        }
    }

    fun initPurchaseEvent() {
        _registerPurchaseEvent.value = UiState.Init
    }
}