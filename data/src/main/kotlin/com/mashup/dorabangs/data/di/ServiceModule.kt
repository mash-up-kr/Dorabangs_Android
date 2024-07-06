package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.network.service.UserService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Reusable
    fun providesUserService(
        retrofit: Retrofit,
    ): UserService = retrofit.create()
}
