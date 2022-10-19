package com.jjsh.smartshopping.data.remote.response

import com.google.gson.annotations.SerializedName
import com.jjsh.smartshopping.domain.model.Product

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    @SerializedName("nprice") val nPrice: Int?,
    @SerializedName("sprice") val sPrice: Int,
    val status: String,
    val sellerId: Long,
    val imagePaths: List<String>
) {
    fun toProduct() = Product(
        id = id,
        name = name,
        description = description,
        nPrice = nPrice ?: sPrice,
        sPrice = sPrice,
        status = status,
        sellerId = sellerId,
        imagePaths = imagePaths,
        thumbnailPath = imagePaths.firstOrNull() ?: ""
    )
}