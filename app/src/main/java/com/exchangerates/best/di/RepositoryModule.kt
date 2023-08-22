package com.exchangerates.best.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.exchangerates.best.data.repository.abstraction.Repository
import com.exchangerates.best.data.repository.implementation.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @[Binds Singleton]
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
