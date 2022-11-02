package com.jjsh.smartshopping.di

import com.jjsh.smartshopping.data.repository.AuthRepositoryImpl
import com.jjsh.smartshopping.data.repository.CartItemRepositoryImpl
import com.jjsh.smartshopping.data.repository.ProductRepositoryImpl
import com.jjsh.smartshopping.data.repository.SearchHistoryRepositoryImpl
import com.jjsh.smartshopping.domain.repository.AuthRepository
import com.jjsh.smartshopping.domain.repository.CartItemRepository
import com.jjsh.smartshopping.domain.repository.ProductRepository
import com.jjsh.smartshopping.domain.repository.SearchHistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

    @Singleton
    @Binds
    abstract fun bindSearchHistoryRepository(
        searchHistoryRepositoryImpl: SearchHistoryRepositoryImpl
    ): SearchHistoryRepository

    @Singleton
    @Binds
    abstract fun bindCartItemRepository(
        cartItemRepositoryImpl: CartItemRepositoryImpl
    ): CartItemRepository
}