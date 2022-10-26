package com.jjsh.smartshopping.domain.repository

import com.jjsh.smartshopping.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {
    suspend fun insertSearchHistory(history: SearchHistory): Result<Unit>
    suspend fun deleteSearchHistory(history: SearchHistory): Result<Unit>
    fun getSearchHistory(): Flow<Result<List<SearchHistory>>>
}