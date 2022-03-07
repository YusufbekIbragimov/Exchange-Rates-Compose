package uz.yusufbekibragimov.valyutauz.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.yusufbekibragimov.valyutauz.data.repository.abstraction.Repository
import uz.yusufbekibragimov.valyutauz.data.repository.implementation.RepositoryImpl
import javax.inject.Singleton

/**
 * Created by Abdulaziz Rasulbek on 4/17/21.
 * Copyright (c) 2021  All rights reserved.
 **/
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @[Binds Singleton]
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}
