package com.jjsh.smartshopping.presentation.ui.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.model.toCheckItem
import com.jjsh.smartshopping.domain.repository.CheckItemRepository
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChecklistRegistrationViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val checkItemRepository: CheckItemRepository
) : ViewModel() {

    private val _getProductEvent = MutableSharedFlow<UiEvent<Product>>()
    val getProductEvent: SharedFlow<UiEvent<Product>> get() = _getProductEvent

    private val _addCheckItemEvent = MutableSharedFlow<UiEvent<Unit>>()
    val addCheckItemEvent: SharedFlow<UiEvent<Unit>> get() = _addCheckItemEvent

    private val _currentProduct = MutableLiveData<Product>()
    val currentProduct: LiveData<Product> get() = _currentProduct

    private val _amount = MutableStateFlow(1)
    val amount: StateFlow<Int> get() = _amount

    private val _totalPrice = MutableStateFlow(0)
    val totalPrice: StateFlow<Int> get() = _totalPrice


    fun getProductDetail(productId: Long) {
        viewModelScope.launch {
            productRepository.getProductItem(productId)
                .onSuccess {
                    _getProductEvent.emit(UiEvent.Success(it))
                    _currentProduct.postValue(it)
                    _amount.value = 1
                    _totalPrice.value = it.sPrice
                }.onFailure {
                    _getProductEvent.emit(UiEvent.Error(it))
                }
        }
    }

    fun addCheckItem() {
        viewModelScope.launch {
            currentProduct.value?.let { product ->
                checkItemRepository.insertCheckItem(product.toCheckItem(amount.value))
                    .onSuccess {
                        _addCheckItemEvent.emit(UiEvent.Success(it))
                        Timber.d("success")
                    }.onFailure {
                        _addCheckItemEvent.emit(UiEvent.Error(it))
                        Timber.e("error")
                    }
            }
        }
    }

    fun updateAmount(isPlus: Boolean) {
        var currentAmount = amount.value
        if (isPlus) {
            if (currentAmount < 100) {
                _amount.value = ++currentAmount
            }
        } else {
            if (currentAmount != 1) {
                _amount.value = --currentAmount
            }
        }
        currentProduct.value?.let {
            _totalPrice.value = it.sPrice * amount.value
        }
    }
}