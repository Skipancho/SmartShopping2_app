package com.jjsh.smartshopping.di

import com.jjsh.smartshopping.data.repository.AuthRepositoryImpl
import com.jjsh.smartshopping.data.repository.ProductRepositoryImpl
import com.jjsh.smartshopping.domain.repository.AuthRepository
import com.jjsh.smartshopping.domain.repository.ProductRepository
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
}