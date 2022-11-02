package com.jjsh.smartshopping.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jjsh.smartshopping.data.local.dao.CartItemDao
import com.jjsh.smartshopping.data.local.dao.CheckItemDao
import com.jjsh.smartshopping.data.local.dao.SearchHistoryDao
import com.jjsh.smartshopping.data.local.dto.CartItemDto
import com.jjsh.smartshopping.data.local.dto.CheckItemDto
import com.jjsh.smartshopping.data.local.dto.SearchHistoryDto

@Database(entities = [SearchHistoryDto::class, CartItemDto::class, CheckItemDto::class], version = 1)
abstract class SmartShoppingDB : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun checkItemDao(): CheckItemDao
}