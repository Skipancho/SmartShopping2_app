package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.domain.model.Purchase

interface PurchaseRepository {
    suspend fun registerPurchaseRecord(cartItems: List<CartItem>): Result<Unit>
    suspend fun getPurchaseRecord(year: Int, month: Int): Result<List<Purchase>>
}