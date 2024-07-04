package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.repository.UserDataStoreRepositoryImpl
import com.mashup.dorabangs.data.repository.save.DoraUrlCheckRepositoryImpl
import com.mashup.dorabangs.domain.repository.UserDataStoreRepository
import com.mashup.dorabangs.domain.repository.save.DoraUrlCheckRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
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
    @Reusable
    abstract fun bindDoraUrlCheckRepository(
        repository: DoraUrlCheckRepositoryImpl,
    ): DoraUrlCheckRepository
}
