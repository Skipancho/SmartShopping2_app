package com.jjsh.smartshopping.di

import com.jjsh.smartshopping.data.repository.*
import com.jjsh.smartshopping.domain.repository.*
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
    abstract fun bindPurchaseRepository(
        purchaseRepositoryImpl: PurchaseRepositoryImpl
    ): PurchaseRepository

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

    @Singleton
    @Binds
    abstract fun bindCheckItemRepository(
        checkItemRepositoryImpl: CheckItemRepositoryImpl
    ): CheckItemRepository
}