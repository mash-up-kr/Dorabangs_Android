package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.repository.UserDataStoreRepositoryImpl
import com.mashup.dorabangs.data.repository.UserRemoteRepositoryImpl
import com.mashup.dorabangs.domain.repository.UserDataStoreRepository
import com.mashup.dorabangs.domain.repository.UserRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserDataStoreRepository(
        userDataStoreRepositoryImpl: UserDataStoreRepositoryImpl,
    ): UserDataStoreRepository

    @Binds
    @Singleton
    abstract fun bindUserRemoteRepository(
        userRemoteRepositoryImpl: UserRemoteRepositoryImpl,
    ): UserRemoteRepository
}
