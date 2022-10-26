package com.jjsh.smartshopping.data.local.datasource

import com.jjsh.smartshopping.data.local.dao.SearchHistoryDao
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
        return withContext(ioDispatcher){
            runCatching {
                searchHistoryDao.insertHistory(historyDto)
            }
        }
    }

    override suspend fun deleteSearchHistory(historyDto: SearchHistoryDto): Result<Unit> {
        return withContext(ioDispatcher){
            runCatching {
                searchHistoryDao.deleteHistory(historyDto)
            }
        }
    }
}