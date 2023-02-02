package com.jjsh.smartshopping.presentation.ui.product.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Review
import com.jjsh.smartshopping.domain.repository.ReviewRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _reviews = MutableStateFlow<UiState<List<Review>>>(UiState.Init)
    val reviews: StateFlow<UiState<List<Review>>> get() = _reviews

    fun getReviews(productId: Long) {
        viewModelScope.launch {
            reviewRepository.getReviews(productId)
                .onSuccess {
                    _reviews.value = UiState.Success(it)
                }.onFailure {
                    _reviews.value = UiState.Error(it)
                }
        }
    }
}