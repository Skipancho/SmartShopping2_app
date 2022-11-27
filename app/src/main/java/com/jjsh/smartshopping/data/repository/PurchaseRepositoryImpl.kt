package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.remote.datasource.RemoteDataSource
import com.jjsh.smartshopping.data.remote.request.toPurchaseRequest
import com.jjsh.smartshopping.domain.model.CartItem
import com.jjsh.smartshopping.domain.model.Purchase
import com.jjsh.smartshopping.domain.repository.PurchaseRepository
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : PurchaseRepository {
    override suspend fun registerPurchaseRecord(cartItems: List<CartItem>): Result<Unit> {
        return remoteDataSource.registerPurchaseRecord(cartItems.map { it.toPurchaseRequest() })
    }

    override suspend fun getPurchaseRecord(year: Int, month: Int): Result<List<Purchase>> {
        return remoteDataSource.getPurchaseRecord(year, month)
            .mapCatching { list ->
                list.map { it.toPurchase() }
            }
    }
}