package com.jjsh.smartshopping.di

import android.content.Context
import androidx.room.Room
import com.jjsh.smartshopping.data.local.SmartShoppingDB
import com.jjsh.smartshopping.data.local.dao.CartItemDao
import com.jjsh.smartshopping.data.local.dao.CheckItemDao
import com.jjsh.smartshopping.data.local.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideSmartShoppingDB(
        @ApplicationContext context: Context
    ): SmartShoppingDB = Room.databaseBuilder(
        context,
        SmartShoppingDB::class.java,
        "smartshopping.db"
    ).build()

    @Provides
    @Singleton
    fun provideSearchHistoryDao(
        database: SmartShoppingDB
    ): SearchHistoryDao = database.searchHistoryDao()

    @Provides
    @Singleton
    fun provideCartItemDao(
        database: SmartShoppingDB
    ): CartItemDao = database.cartItemDao()

    @Provides
    @Singleton
    fun provideCheckItemDao(
        database: SmartShoppingDB
    ): CheckItemDao = database.checkItemDao()
}