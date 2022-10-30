package com.jjsh.smartshopping.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CART_ITEM")
data class CartItemDto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val productId: Long,
    val userCode: Long,
    val name: String,
    val nPrice: String,
    val sPrice: String,
    val amount: Int,
    val thumbnailUrl: String,
    val isChecked: Boolean = true,
    val time : Long
)
