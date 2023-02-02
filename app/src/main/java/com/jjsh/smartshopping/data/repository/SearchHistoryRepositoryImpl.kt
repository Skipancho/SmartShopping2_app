package com.jjsh.smartshopping.data.repository

import com.jjsh.smartshopping.data.local.datasource.LocalDataSource
import com.jjsh.smartshopping.data.local.dto.toDto
import com.jjsh.smartshopping.domain.model.SearchHistory
import com.jjsh.smartshopping.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : SearchHistoryRepository {

    override suspend fun insertSearchHistory(history: SearchHistory): Result<Unit> {
        return localDataSource.insertSearchHistory(history.toDto())
    }

    override suspend fun deleteSearchHistory(vararg history: SearchHistory): Result<Unit> {
        val histories = history.map { it.toDto() }.toTypedArray()
        return localDataSource.deleteSearchHistory(*histories)
    }

    override fun getSearchHistory(): Flow<Result<List<SearchHistory>>> {
        return localDataSource.getSearchHistory().map { result ->
            result.mapCatching { list ->
                list.map { historyDto ->
                    historyDto.toSearchHistory()
                }
            }
        }
    }
}