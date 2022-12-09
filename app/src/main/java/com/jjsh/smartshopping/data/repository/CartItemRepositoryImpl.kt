package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.auth.Auth
import com.jjsh.smartshopping.data.local.datasource.LocalDataSource
import com.jjsh.smartshopping.data.local.dto.toDto
import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.domain.repository.CartItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartItemRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val auth: Auth
) : CartItemRepository {

    override fun getCartItems(): Flow<Result<List<CartItem>>> {
        return localDataSource.getCartItems(auth.userCode)
            .map { result ->
                result.mapCatching { list ->
                    list.map {
                        it.toCartItem()
                    }
                }
            }
    }

    override suspend fun insertCartItem(vararg cartItem: CartItem): Result<Unit> {
        val items = cartItem.map {
            val defaultItem = localDataSource.getCartItem(auth.userCode, it.productId).getOrNull()
            val defaultAmount = defaultItem?.amount ?: 0
            val defaultId = defaultItem?.id ?: 0
            it.setAmount(defaultAmount + it.amount).toDto(auth.userCode).setId(defaultId)
        }.toTypedArray()
        return localDataSource.insertCartItem(*items)
    }

    override suspend fun updateCartItem(vararg cartItem: CartItem): Result<Unit> {
        val items = cartItem.map { it.toDto(auth.userCode) }.toTypedArray()
        return localDataSource.insertCartItem(*items)
    }

    override suspend fun deleteCartItem(vararg cartItem: CartItem): Result<Unit> {
        val items = cartItem.map { it.toDto(auth.userCode) }.toTypedArray()
        return localDataSource.deleteCartItem(*items)
    }
}