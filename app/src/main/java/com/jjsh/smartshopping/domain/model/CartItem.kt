package com.jjsh.smartshopping.domain.model

data class CartItem(
    val id: Long = -1,
    val productId: Long,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    val amount: Int,
    val thumbnailUrl: String,
    val isChecked: Boolean = true
){
    fun setAmount(amount: Int) : CartItem =
        CartItem(id, productId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked)
}

fun Product.toCartItem(amount: Int): CartItem =
    CartItem(
        productId = id,
        name = name,
        nPrice = nPrice,
        sPrice = sPrice,
        amount = amount,
        thumbnailUrl = thumbnailPath
    )
