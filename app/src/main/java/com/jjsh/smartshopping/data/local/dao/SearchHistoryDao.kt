package com.jjsh.smartshopping.data.local.dao

import androidx.room.*
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: SearchHistoryDto)

    @Delete
    suspend fun deleteHistory(history: SearchHistoryDto)

    @Query("SELECT * FROM `SEARCH_HISTORY` ORDER BY `time` DESC")
    fun getHistory(): Flow<List<SearchHistoryDto>>
}