package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.repository.FolderRepositoryImpl
import com.mashup.dorabangs.data.repository.UserRepositoryImpl
import com.mashup.dorabangs.data.repository.save.DoraUrlCheckRepositoryImpl
import com.mashup.dorabangs.domain.repository.FolderRepository
import com.mashup.dorabangs.domain.repository.UserRepository
import com.mashup.dorabangs.domain.repository.save.DoraUrlCheckRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Reusable
    abstract fun bindUserRepository(
        repositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    @Reusable
    abstract fun bindFolderRepository(
        repository: FolderRepositoryImpl,
    ): FolderRepository

    @Reusable
    @Binds
    abstract fun bindDoraUrlCheckRepository(
        repository: DoraUrlCheckRepositoryImpl,
    ): DoraUrlCheckRepository
}
