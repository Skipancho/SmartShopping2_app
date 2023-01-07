package com.jjsh.smartshopping.presentation.ui.registration.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.domain.repository.ReviewRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ReviewRegistrationViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private var productId: Long = -1L
    private var purchaseId: Long = -1L

    private var _editMode = MutableStateFlow(false)
    val editMode: StateFlow<Boolean> get() = _editMode

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> get() = _productName

    private val _thumbnailUrl = MutableStateFlow("")
    val thumbnailUrl: StateFlow<String> get() = _thumbnailUrl

    private val _reviewScore = MutableStateFlow(0)
    val reviewScore: StateFlow<Int> get() = _reviewScore

    private val _showScoreFragment = MutableStateFlow(true)
    val showScoreFragment: StateFlow<Boolean> get() = _showScoreFragment

    val reviewText = MutableStateFlow("")

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    fun getProduct(productId: Long, purchaseId: Long) {
        this.productId = productId
        this.purchaseId = purchaseId
        _editMode.value = false
        viewModelScope.launch {
            productRepository.getProductItem(productId)
                .onSuccess {
                    _productName.value = it.name
                    _thumbnailUrl.value = it.thumbnailPath
                }
        }
    }

    fun getReview(reviewId: Long) {
        _editMode.value = true
        _showScoreFragment.value = false
        viewModelScope.launch {
            reviewRepository.getReview(reviewId)
                .onSuccess {
                    purchaseId = it.purchaseId
                    _productName.value = it.productName
                    _thumbnailUrl.value = it.thumbnailUrl
                    _reviewScore.value = it.score
                    reviewText.value = it.reviewText
                }.onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }

    fun setScore(score: Int) {
        _reviewScore.value = score
        _showScoreFragment.value = false
    }

    fun showScore() {
        _showScoreFragment.value = true
    }

    fun reviewBtnEvent() {
        when(editMode.value) {
            true -> editReview()
            false -> writeReview()
        }
    }

    private fun writeReview() {
        if (uiState.value is UiState.Loading) return
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            reviewRepository.writeReview(
                productId = productId,
                purchaseId = purchaseId,
                score = reviewScore.value,
                reviewText = reviewText.value
            ).onSuccess {
                _uiState.value = UiState.Success(it)
            }.onFailure {
                _uiState.value = UiState.Error(it)
            }
        }
    }

    private fun editReview() {
        if (uiState.value is UiState.Loading) return
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            reviewRepository.updateReview(
                productId = productId,
                purchaseId = purchaseId,
                score = reviewScore.value,
                reviewText = reviewText.value
            ).onSuccess {
                _uiState.value = UiState.Success(it)
            }.onFailure {
                _uiState.value = UiState.Error(it)
            }
        }
    }
}