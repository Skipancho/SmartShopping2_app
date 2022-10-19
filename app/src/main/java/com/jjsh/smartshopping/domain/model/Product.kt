package com.jjsh.smartshopping.domain.model

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val nPrice: Int,
    val sPrice: Int,
    val status: String,
    val sellerId: Long,
    val imagePaths: List<String>
)
