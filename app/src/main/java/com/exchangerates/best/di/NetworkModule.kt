package com.exchangerates.best.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.exchangerates.best.data.cache.abstraction.UserPreferenceManager
import com.exchangerates.best.data.remote.NetworkService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://cbu.uz/"

    @Singleton
    @Provides
    fun provideRetrofit(factory: MoshiConverterFactory, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .client(okHttpClient)
            .build()
    }



    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        beetoInterceptor: Interceptor,
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(beetoInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    fun provideBeetoInterceptor(userPreferenceManager: UserPreferenceManager): Interceptor {
        return Interceptor {
            val request1 = it.request()
            val request = request1.newBuilder()
//                .addHeader(AUTHORIZATION, "${userPreferenceManager.token}")
//                .addHeader(HttpHeaders.ACCEPT_LANGUAGE, "uz")

            val response = it.proceed(request.build())
            Log.d("TAGTAG", "provideBeetoInterceptor: ${response.code}")
            response
        }
    }

    @Provides
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).maxContentLength(250_000L)
            .alwaysReadResponseBody(true).build()
    }


    @[Provides Singleton]
    fun provideNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }
}