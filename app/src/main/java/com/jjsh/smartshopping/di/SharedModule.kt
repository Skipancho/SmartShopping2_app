package com.jjsh.smartshopping.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.jjsh.smartshopping.data.auth.AuthImpl
import com.jjsh.smartshopping.data.auth.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedModule {
    @Singleton
    @Provides
    fun provideSharedPrefs(
        @ApplicationContext context: Context
    ): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun provideAuthInformation(
        prefs : SharedPreferences
    ): Auth = AuthImpl(prefs)
}