package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckRemoteDataSource
import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mashup.dorabangs.data.datasource.local.api.UserLocalDataSource
import com.mashup.dorabangs.data.datasource.local.impl.UserLocalDataSourceImpl
import com.mashup.dorabangs.data.datasource.remote.api.UserRemoteDataSource
import com.mashup.dorabangs.data.datasource.remote.impl.UserRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Reusable
    abstract fun bindsUrlCheckDataSource(
        repository: DoraUrlCheckRemoteDataSourceImpl,
    ): DoraUrlCheckRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(
        dataSource: UserLocalDataSourceImpl,
    ): UserLocalDataSource

    @Binds
    @Singleton
    abstract fun bindUserRemoteDataSource(
        dataSource: UserRemoteDataSourceImpl,
    ): UserRemoteDataSource
}
