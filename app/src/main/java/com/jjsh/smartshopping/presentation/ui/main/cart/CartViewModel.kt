package com.jjsh.smartshopping.presentation.ui.main.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.domain.usecase.GetCartItemsUseCase
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase
) : ViewModel() {
    private val _cartList = MutableStateFlow<UiState<List<CartItem>>>(UiState.Init)
    val cartList: StateFlow<UiState<List<CartItem>>> get() = _cartList

    fun getCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collectLatest { result ->
                result.onSuccess {
                    _cartList.value = UiState.Success(it)
                }.onFailure {
                    _cartList.value = UiState.Error(it)
                }
            }
        }
    }
}