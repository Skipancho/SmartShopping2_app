package com.jjsh.smartshopping.presentation.ui.registration.product

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.domain.model.ProductRegistrationInfo
import com.jjsh.smartshopping.domain.usecase.RegisterProductUseCase
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


@HiltViewModel
class ProductRegistrationViewModel @Inject constructor(
    private val registerProductUseCase: RegisterProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    val productName = MutableStateFlow("test dog")
    val productDescription = MutableStateFlow("test dog dog")
    val productNPrice = MutableStateFlow("10000")
    val productSPrice = MutableStateFlow("6000")
    val productBarcode = MutableStateFlow("666666")

    private val _progressOn = MutableStateFlow(false)
    val progressOn: StateFlow<Boolean> get() = _progressOn

    private var _categoryNumber = 0
    private val categoryNumber get() = _categoryNumber

    private val images = mutableListOf<Uri>()
    private val imageFiles = mutableListOf<File>()

    fun registerProduct() {
        if (uiState.value is UiState.Loading) return
        if (hasEmpty()) {
            _uiState.value = UiState.Error(ErrorException.EmptyMemberException)
            return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            setProgressOn(true)
            registerProductUseCase(
                productRegistrationInfo = ProductRegistrationInfo(
                    productName = productName.value,
                    description = productDescription.value,
                    nPrice = productNPrice.value.toIntOrNull(),
                    sPrice = productSPrice.value.toInt(),
                    categoryId = categoryNumber,
                    barcode = productBarcode.value.toLong()
                ),
                images = imageFiles
            ).onSuccess {
                _uiState.value = UiState.Success(it)
                setProgressOn(false)
            }.onFailure {
                it.printStackTrace()
                _uiState.value = UiState.Error(it)
                setProgressOn(false)
            }
        }
    }

    fun addImageFromUri(uri: Uri, complete: (List<Uri>) -> Unit) {
        images.add(uri)
        complete(images)
    }

    fun setCategoryNumber(number: Int) {
        _categoryNumber = number
    }

    private fun setProgressOn(progressOn: Boolean) {
        _progressOn.value = progressOn
    }

    fun addImageFile(imageFile : File) {
        imageFiles.add(imageFile)
    }

    private fun hasEmpty() = productName.value.isEmpty() ||
            productDescription.value.isEmpty() ||
            productNPrice.value.isEmpty() ||
            productSPrice.value.isEmpty() ||
            productBarcode.value.isEmpty()
}