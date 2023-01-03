package com.jjsh.smartshopping.presentation.ui.registration.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.Product
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.domain.repository.ReviewRepository
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

    private var purchaseId: Long = -1L

    private val _currentProduct = MutableStateFlow(Product.nullProduct)
    val currentProduct: StateFlow<Product> get() = _currentProduct

    private val _reviewScore = MutableStateFlow(0)
    val reviewScore: StateFlow<Int> get() = _reviewScore

    private val _showScoreFragment = MutableStateFlow(true)
    val showScoreFragment : StateFlow<Boolean> get() = _showScoreFragment

    val reviewText = MutableStateFlow("")

    fun getProduct(productId: Long, purchaseId: Long) {
        this.purchaseId = purchaseId
        viewModelScope.launch {
            productRepository.getProductItem(productId)
                .onSuccess {
                    _currentProduct.value = it
                }
        }
    }

    fun setScore(score: Int) {
        _reviewScore.value = score
        _showScoreFragment.value = false
    }

    fun showScore(){
        _showScoreFragment.value = true
    }
}