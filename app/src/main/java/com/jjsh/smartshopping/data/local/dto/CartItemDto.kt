package com.jjsh.smartshopping.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jjsh.smartshopping.domain.model.CartItem
import java.util.*

@Entity(tableName = "CART_ITEM")
data class CartItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val productId: Long,
    val userCode: Long,
    val name: String,
    val nPrice: Int,
    val sPrice: Int,
    val amount: Int,
    val thumbnailUrl: String,
    val isChecked: Boolean,
    val time: Long
) {
    fun toCartItem(): CartItem =
        CartItem(id, productId, name, nPrice, sPrice, amount, thumbnailUrl, isChecked)
}

fun CartItem.toDto(userCode: Long): CartItemDto = CartItemDto(
    id, productId, userCode, name, nPrice, sPrice, amount, thumbnailUrl, isChecked, Date().time
)
