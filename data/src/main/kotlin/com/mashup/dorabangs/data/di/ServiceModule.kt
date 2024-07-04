package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.network.service.FolderService
import com.mashup.dorabangs.data.network.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesUserService(
        retrofit: Retrofit,
    ): UserService = retrofit.create()

    @Provides
    @Singleton
    fun provideFolderService(
        retrofit: Retrofit,
    ): FolderService =
        retrofit.create(FolderService::class.java)
}
