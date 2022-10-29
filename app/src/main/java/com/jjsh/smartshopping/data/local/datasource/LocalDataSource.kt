package com.jjsh.smartshopping.data.local.datasource

import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import kotlinx.coroutines.flow.Flow


interface LocalDataSource {
    fun getSearchHistory(): Flow<Result<List<SearchHistoryDto>>>
    suspend fun insertSearchHistory(historyDto: SearchHistoryDto): Result<Unit>
    suspend fun deleteSearchHistory(vararg historyDto: SearchHistoryDto): Result<Unit>
}