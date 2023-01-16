package com.jjsh.smartshopping.presentation.ui.registration.product

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductRegistrationViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    val productName = MutableStateFlow("")
    val productDescription = MutableStateFlow("")
    val productNPrice = MutableStateFlow("")
    val productSPrice = MutableStateFlow("")
    val productBarcode = MutableStateFlow("")

    private val images = mutableListOf<Uri>()

    fun registerProduct() {
        if (hasEmpty()) {
            _uiState.value = UiState.Error(ErrorException.EmptyMemberException)
            return
        }
        viewModelScope.launch {

        }
    }

    fun addImageFromUri(uri: Uri, complete: (List<Uri>) -> Unit) {
        images.add(uri)
        complete(images)
    }

    private fun hasEmpty() = productName.value.isEmpty() ||
            productDescription.value.isEmpty() ||
            productNPrice.value.isEmpty() ||
            productSPrice.value.isEmpty() ||
            productBarcode.value.isEmpty()

}