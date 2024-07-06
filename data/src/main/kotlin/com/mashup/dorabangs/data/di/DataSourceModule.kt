package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.datasource.local.api.UserLocalDataSource
import com.mashup.dorabangs.data.datasource.local.impl.UserLocalDataSourceImpl
import com.mashup.dorabangs.data.datasource.remote.api.UserRemoteDataSource
import com.mashup.dorabangs.data.datasource.remote.impl.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Reusable
    abstract fun bindUserLocalDataSource(
        dataSource: UserLocalDataSourceImpl,
    ): UserLocalDataSource

    @Binds
    @Reusable
    abstract fun bindUserRemoteDataSource(
        dataSource: UserRemoteDataSourceImpl,
    ): UserRemoteDataSource
}
