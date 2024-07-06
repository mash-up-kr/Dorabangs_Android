package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.repository.UserRepositoryImpl
import com.mashup.dorabangs.domain.repository.UserRepository
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
}
