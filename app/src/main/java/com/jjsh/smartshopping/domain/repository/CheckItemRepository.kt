package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.CheckItem
import kotlinx.coroutines.flow.Flow

interface CheckItemRepository {
    fun getCheckItems(): Flow<Result<List<CheckItem>>>
    suspend fun insertCheckItem(vararg checkItem: CheckItem): Result<Unit>
    suspend fun updateCheckItem(vararg checkItem: CheckItem): Result<Unit>
    suspend fun deleteCheckItem(vararg checkItem: CheckItem): Result<Unit>
}