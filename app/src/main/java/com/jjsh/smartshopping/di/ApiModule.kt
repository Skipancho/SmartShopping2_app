package com.jjsh.smartshopping.di

import com.jjsh.smartshopping.BuildConfig
import com.jjsh.smartshopping.data.remote.ApiTokenInterceptor
import com.jjsh.smartshopping.data.remote.RefreshTokenInterceptor
import com.jjsh.smartshopping.data.remote.TokenAuthenticator
import com.jjsh.smartshopping.data.remote.api.AuthService
import com.jjsh.smartshopping.data.remote.api.ProductService
import com.jjsh.smartshopping.data.remote.api.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @HttpClient
    @Provides
    @Singleton
    fun provideHttpClient(
        apiTokenInterceptor: ApiTokenInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                }
            )
            addInterceptor(apiTokenInterceptor)
            authenticator(tokenAuthenticator)
        }.build()

    @RefreshClient
    @Provides
    @Singleton
    fun provideRefreshClient(
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                }
            )
            addInterceptor(refreshTokenInterceptor)
        }.build()

    @HttpClientRetrofit
    @Provides
    @Singleton
    fun provideHttpClientRetrofit(
        @HttpClient httpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("${BuildConfig.API_HOST}:${BuildConfig.API_PORT}")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    @RefreshClientRetrofit
    @Provides
    @Singleton
    fun provideRefreshClientRetrofit(
        @RefreshClient httpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl("${BuildConfig.API_HOST}:${BuildConfig.API_PORT}")
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    @Provides
    @Singleton
    fun provideAuthService(
        @HttpClientRetrofit retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideProductService(
        @HttpClientRetrofit retrofit: Retrofit
    ): ProductService = retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideTokenService(
        @RefreshClientRetrofit retrofit: Retrofit
    ): TokenService = retrofit.create(TokenService::class.java)
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class HttpClient

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RefreshClient

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class HttpClientRetrofit

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RefreshClientRetrofit