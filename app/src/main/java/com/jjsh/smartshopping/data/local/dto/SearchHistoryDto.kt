package com.jjsh.smartshopping.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jjsh.smartshopping.domain.model.SearchHistory
import java.util.*

@Entity(tableName = "SEARCH_HISTORY")
data class SearchHistoryDto(
    @PrimaryKey(autoGenerate = false) val text: String,
    val time : Long
) {
    fun toSearchHistory(): SearchHistory = SearchHistory(text)
}

fun SearchHistory.toDto() : SearchHistoryDto = SearchHistoryDto(text,Date().time)