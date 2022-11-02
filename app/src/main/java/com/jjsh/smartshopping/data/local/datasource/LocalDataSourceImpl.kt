package com.jjsh.smartshopping.data.local.datasource

import com.jjsh.smartshopping.data.local.dao.CartItemDao
import com.jjsh.smartshopping.data.local.dao.SearchHistoryDao
import com.jjsh.smartshopping.data.local.dto.CartItemDto
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import com.jjsh.smartshopping.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val searchHistoryDao: SearchHistoryDao,
    private val cartItemDao: CartItemDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : LocalDataSource {

    override fun getSearchHistory(): Flow<Result<List<SearchHistoryDto>>> {
        return searchHistoryDao
            .getHistory()
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(Exception()))
            }.flowOn(ioDispatcher)
    }

    override suspend fun insertSearchHistory(historyDto: SearchHistoryDto): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                searchHistoryDao.insertHistory(historyDto)
            }
        }
    }

    override suspend fun deleteSearchHistory(vararg historyDto: SearchHistoryDto): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching {
                searchHistoryDao.deleteHistory(*historyDto)
            }
        }
    }

    override fun getCartItems(userCode: Long): Flow<Result<List<CartItemDto>>> {
        return cartItemDao
            .getItems(userCode)
            .map {
                Result.success(it)
            }.catch {
                emit(Result.failure(Exception()))
            }.flowOn(ioDispatcher)
    }

    override suspend fun getCartItem(userCode: Long, itemId: Long): Result<CartItemDto> {
        return withContext(ioDispatcher) {
            runCatching { cartItemDao.getItem(userCode, itemId) }
        }
    }

    override suspend fun insertCartItem(vararg cartItemDto: CartItemDto): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching { cartItemDao.insertItem(*cartItemDto) }
        }
    }

    override suspend fun deleteCartItem(vararg cartItemDto: CartItemDto): Result<Unit> {
        return withContext(ioDispatcher) {
            runCatching { cartItemDao.deleteItem(*cartItemDto) }
        }
    }
}