package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.common.Auth
import com.jjsh.smartshopping.data.local.datasource.LocalDataSource
import com.jjsh.smartshopping.data.local.dto.toDto
import com.jjsh.smartshopping.domain.model.CheckItem
import com.jjsh.smartshopping.domain.repository.CheckItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CheckItemRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val auth: Auth
) : CheckItemRepository {
    override fun getCheckItems(): Flow<Result<List<CheckItem>>> {
        return localDataSource.getCheckItems(auth.userCode).map { result ->
            result.mapCatching { list ->
                list.map {
                    it.toCheckItem()
                }
            }
        }
    }

    override suspend fun insertCheckItem(vararg checkItem: CheckItem): Result<Unit> {
        val items = checkItem.map {
            val defaultItem = localDataSource.getCheckItem(auth.userCode, it.productId).getOrNull()
            val defaultAmount = defaultItem?.amount ?: 0
            val defaultId = defaultItem?.id ?: 0
            it.setAmount(defaultAmount + it.amount).toDto(auth.userCode).setId(defaultId)
        }.toTypedArray()
        return localDataSource.insertCheckItem(*items)
    }

    override suspend fun updateCheckItem(vararg checkItem: CheckItem): Result<Unit> {
        val items = checkItem.map { it.toDto(auth.userCode) }.toTypedArray()
        return localDataSource.insertCheckItem(*items)
    }

    override suspend fun deleteCheckItem(vararg checkItem: CheckItem): Result<Unit> {
        val items = checkItem.map { it.toDto(auth.userCode) }.toTypedArray()
        return localDataSource.deleteCheckItem(*items)
    }
}