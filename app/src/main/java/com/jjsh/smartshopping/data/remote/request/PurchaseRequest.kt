package com.jjsh.smartshopping.data.remote.request

data class PurchaseRequest(
    val productId: Long,
    val productName: String,
    val price: Int,
    val amount: Int,
    val categoryId: Int
)
