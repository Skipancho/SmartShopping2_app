package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartItemRepository {
    fun getCartItems(): Flow<Result<List<CartItem>>>
    suspend fun insertCartItem(vararg cartItem: CartItem): Result<Unit>
    suspend fun updateCartItem(vararg cartItem: CartItem): Result<Unit>
    suspend fun deleteCartItem(vararg cartItem: CartItem): Result<Unit>
}