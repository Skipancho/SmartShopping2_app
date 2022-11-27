package com.jjsh.smartshopping.data.remote.response

data class PurchaseResponse(
    val id: Long,
    val category: String,
    val productId: Long,
    val productName: String,
    val price: Int,
    val amount: Int
)
