package com.jjsh.smartshopping.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jjsh.smartshopping.domain.model.CartItem

@Entity(tableName = "CART_ITEM")
data class CartItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val userCode: Long,
    val categoryId: Int,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    val amount: Int,
    val thumbnailUrl: String,
    val isChecked: Boolean
) {
    fun toCartItem() = CartItem(
        id, productId, categoryId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked
    )

    fun setId(id: Long) = CartItemDto(
        id, productId, userCode, categoryId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked
    )
}

fun CartItem.toDto(userCode: Long): CartItemDto = CartItemDto(
    id, productId, userCode, categoryId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked
)
