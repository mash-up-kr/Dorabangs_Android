package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.datasource.local.api.UserLocalDataSource
import com.mashup.dorabangs.data.datasource.local.impl.UserLocalDataSourceImpl
import com.mashup.dorabangs.data.datasource.remote.api.DoraUrlCheckRemoteDataSource
import com.mashup.dorabangs.data.datasource.remote.api.FolderRemoteDataSource
import com.mashup.dorabangs.data.datasource.remote.api.PostsRemoteDataSource
import com.mashup.dorabangs.data.datasource.remote.api.UserRemoteDataSource
import com.mashup.dorabangs.data.datasource.remote.impl.DoraUrlCheckRemoteDataSourceImpl
import com.mashup.dorabangs.data.datasource.remote.impl.FolderRemoteDataSourceImpl
import com.mashup.dorabangs.data.datasource.remote.impl.PostsRemoteDataSourceImpl
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
    abstract fun bindsUrlCheckDataSource(
        repository: DoraUrlCheckRemoteDataSourceImpl,
    ): DoraUrlCheckRemoteDataSource

    @Binds
    @Reusable
    abstract fun bindsUserLocalDataSource(
        dataSource: UserLocalDataSourceImpl,
    ): UserLocalDataSource

    @Binds
    @Reusable
    abstract fun bindsUserRemoteDataSource(
        dataSource: UserRemoteDataSourceImpl,
    ): UserRemoteDataSource

    @Binds
    @Reusable
    abstract fun bindsFolderRemoteDataSource(
        dataSource: FolderRemoteDataSourceImpl,
    ): FolderRemoteDataSource

    @Binds
    @Reusable
    abstract fun bindsPostsRemoteDataSource(
        dataSource: PostsRemoteDataSourceImpl,
    ): PostsRemoteDataSource
}
