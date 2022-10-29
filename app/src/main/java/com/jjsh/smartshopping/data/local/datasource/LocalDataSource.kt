package com.jjsh.smartshopping.data.local.datasource

import com.jjsh.smartshopping.data.local.dto.CartItemDto
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    fun getSearchHistory(): Flow<Result<List<SearchHistoryDto>>>
    suspend fun insertSearchHistory(historyDto: SearchHistoryDto): Result<Unit>
    suspend fun deleteSearchHistory(vararg historyDto: SearchHistoryDto): Result<Unit>

    fun getCartItems(userCode: Long): Flow<Result<List<CartItemDto>>>
    suspend fun getCartItem(userCode: Long, itemId: Long): Result<CartItemDto>
    suspend fun insertCartItem(vararg cartItemDto: CartItemDto): Result<Unit>
    suspend fun deleteCartItem(vararg cartItemDto: CartItemDto): Result<Unit>
}