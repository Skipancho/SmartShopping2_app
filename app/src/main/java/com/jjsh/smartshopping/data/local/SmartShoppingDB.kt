package com.jjsh.smartshopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jjsh.smartshopping.data.local.dao.SearchHistoryDao
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto

@Database(entities = [SearchHistoryDto::class], version = 1)
abstract class SmartShoppingDB : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}