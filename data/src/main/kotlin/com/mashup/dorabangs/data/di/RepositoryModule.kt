package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.repository.UserRepositoryImpl
import com.mashup.dorabangs.data.repository.save.DoraUrlCheckRepositoryImpl
import com.mashup.dorabangs.domain.repository.UserRepository
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
    abstract fun bindUserRepository(
        repositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Reusable
    @Binds
    abstract fun bindDoraUrlCheckRepository(
        repository: DoraUrlCheckRepositoryImpl,
    ): DoraUrlCheckRepository
}
