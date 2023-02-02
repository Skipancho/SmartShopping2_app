package com.jjsh.smartshopping.domain.model

data class CartItem(
    val id: Long = 0,
    val productId: Long,
    val categoryId : Int,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    var amount: Int,
    val thumbnailUrl: String,
    var isChecked: Boolean = false,
    val inCheckList: Boolean = true
) {
    val sale get() = (100 * (nPrice - sPrice)) / nPrice
    val totalPrice get() = amount * sPrice

    fun setAmount(amount: Int): CartItem = CartItem(
        id, productId,categoryId ,name, nPrice, sPrice, amount, thumbnailUrl, isChecked
    )

    fun setChecked(isChecked: Boolean): CartItem = CartItem(
        id, productId,categoryId ,name, nPrice, sPrice, amount, thumbnailUrl, isChecked
    )

    fun setInCheckList(inCheckList: Boolean): CartItem = CartItem(
        id, productId,categoryId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked, inCheckList
    )
}

fun Product.toCartItem(amount: Int): CartItem =
    CartItem(
        productId = id,
        categoryId = categoryId,
        name = name,
        nPrice = nPrice,
        sPrice = sPrice,
        amount = amount,
        thumbnailUrl = thumbnailPath
    )
