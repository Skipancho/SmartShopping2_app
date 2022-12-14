package com.jjsh.smartshopping.domain.model

data class CheckItem(
    val id: Long = 0L,
    val productId: Long,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    var amount: Int,
    val thumbnailUrl: String,
    var isChecked: Boolean = false,
    val isInCart: Boolean = false
) {
    val sale get() = (100 * (nPrice - sPrice)) / nPrice
    val totalPrice get() = amount * sPrice

    fun setAmount(amount: Int): CheckItem =
        CheckItem(id, productId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked, isInCart)

    fun setChecked(isChecked: Boolean): CheckItem =
        CheckItem(id, productId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked, isInCart)

    fun setInCart(isInCart: Boolean): CheckItem =
        CheckItem(id, productId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked, isInCart)
}

fun Product.toCheckItem(amount: Int) : CheckItem = CheckItem(
    productId = id,
    name = name,
    nPrice = nPrice,
    sPrice = sPrice,
    amount = amount,
    thumbnailUrl = thumbnailPath
)
