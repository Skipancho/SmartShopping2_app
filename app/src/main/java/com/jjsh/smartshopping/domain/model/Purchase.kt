package com.jjsh.smartshopping.domain.model

data class Purchase(
    val id: Long,
    val category: String,
    val productId: Long,
    val productName: String,
    val price: Int,
    val amount: Int,
    val isReviewed: Boolean
)
