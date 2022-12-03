package com.jjsh.smartshopping.data.fake.dao

import com.jjsh.smartshopping.data.local.dao.SearchHistoryDao
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSearchHistoryDao(
    private val fakeSearchHistoryDtoList : MutableList<SearchHistoryDto>
) : SearchHistoryDao {
    override suspend fun insertHistory(history: SearchHistoryDto) {
        fakeSearchHistoryDtoList.add(history)
    }

    override suspend fun deleteHistory(vararg history: SearchHistoryDto) {
        fakeSearchHistoryDtoList.removeAll(history.toSet())
    }

    override fun getHistory(): Flow<List<SearchHistoryDto>> {
        return flow {
            emit(fakeSearchHistoryDtoList)
        }
    }
}