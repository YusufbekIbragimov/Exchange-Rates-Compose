package uz.yusufbekibragimov.valyutauz.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.yusufbekibragimov.valyutauz.data.cache.abstraction.UserPreferenceManager
import uz.yusufbekibragimov.valyutauz.data.cache.implementation.UserPreferenceManagerImpl
import javax.inject.Singleton

/**
 * Created by Abdulaziz Rasulbek on 4/16/21.
 * Copyright (c) 2021  All rights reserved.
 **/

@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModuleBinds {

    @Singleton
    @Binds
    abstract fun bindUserPreferenceManager(sourceImpl: UserPreferenceManagerImpl): UserPreferenceManager

}