package com.jjsh.smartshopping.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SEARCH_HISTORY")
data class SearchHistoryDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val text : String
)