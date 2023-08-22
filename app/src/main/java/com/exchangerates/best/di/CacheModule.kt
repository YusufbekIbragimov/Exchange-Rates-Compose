package com.exchangerates.best.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.exchangerates.best.data.cache.abstraction.UserPreferenceManager
import com.exchangerates.best.data.cache.implementation.UserPreferenceManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModuleBinds {

    @Singleton
    @Binds
    abstract fun bindUserPreferenceManager(sourceImpl: UserPreferenceManagerImpl): UserPreferenceManager

}