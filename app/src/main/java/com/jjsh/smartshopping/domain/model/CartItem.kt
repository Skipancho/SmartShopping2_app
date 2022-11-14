package com.jjsh.smartshopping.domain.model

data class CartItem(
    val id: Long = 0,
    val productId: Long,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    var amount: Int,
    val thumbnailUrl: String,
    val isChecked: Boolean = true
) {
    val sale get() = (100 * (nPrice - sPrice)) / nPrice

    fun setAmount(amount: Int): CartItem =
        CartItem(id, productId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked)

    fun setChecked(isChecked: Boolean): CartItem =
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
