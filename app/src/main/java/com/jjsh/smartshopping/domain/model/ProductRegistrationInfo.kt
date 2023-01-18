package com.jjsh.smartshopping.domain.model

data class ProductRegistrationInfo(
    val productName: String,
    val description: String,
    val nPrice: Int?,
    val sPrice: Int,
    val categoryId: Int,
    val barcode: Long
)
