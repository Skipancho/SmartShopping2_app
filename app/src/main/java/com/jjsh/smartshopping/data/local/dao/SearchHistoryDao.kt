package com.jjsh.smartshopping.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Insert
    suspend fun insertHistory(history: SearchHistoryDto)

    @Delete
    suspend fun deleteHistory(history: SearchHistoryDto)

    @Query("SELECT * FROM `SEARCH_HISTORY`")
    fun getHistory(): Flow<List<SearchHistoryDto>>
}