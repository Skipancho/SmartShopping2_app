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
    constructor(searchHistory: SearchHistory) : this(searchHistory.text, Date().time)

    fun toSearchHistory(): SearchHistory = SearchHistory(text)
}