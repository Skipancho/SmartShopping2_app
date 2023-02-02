package com.jjsh.smartshopping.data.remote.request

import com.jjsh.smartshopping.domain.model.CartItem

data class PurchaseRequest(
    val productId: Long,
    val productName: String,
    val price: Int,
    val amount: Int,
    val categoryId: Int
)

fun CartItem.toPurchaseRequest() = PurchaseRequest(
    productId, name, totalPrice, amount, categoryId
)