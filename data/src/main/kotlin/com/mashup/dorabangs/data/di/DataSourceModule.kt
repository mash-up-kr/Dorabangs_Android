package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckRemoteDataSource
import com.mashup.dorabangs.data.datasource.save.DoraUrlCheckRemoteDataSourceImpl
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
    abstract fun bindsUrlCheckDataSource(
        repository: DoraUrlCheckRemoteDataSourceImpl,
    ): DoraUrlCheckRemoteDataSource
}
