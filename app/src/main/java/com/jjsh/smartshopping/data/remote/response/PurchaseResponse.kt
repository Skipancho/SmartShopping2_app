package com.jjsh.smartshopping.data.remote.response

import com.google.gson.annotations.SerializedName
import com.jjsh.smartshopping.domain.model.Purchase

data class PurchaseResponse(
    val id: Long,
    val category: String,
    val productId: Long,
    val productName: String,
    val price: Int,
    val amount: Int,
    @SerializedName("reviewed") val isReviewed: Boolean
) {
    fun toPurchase() = Purchase(
        id, category, productId, productName, price, amount, isReviewed
    )
}