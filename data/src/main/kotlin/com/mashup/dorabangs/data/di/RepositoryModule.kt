package com.mashup.dorabangs.data.di

import com.mashup.dorabangs.data.repository.AIClassificationRepositoryImpl
import com.mashup.dorabangs.data.repository.FolderRepositoryImpl
import com.mashup.dorabangs.data.repository.PostsRepositoryImpl
import com.mashup.dorabangs.data.repository.UserRepositoryImpl
import com.mashup.dorabangs.data.repository.save.DoraUrlCheckRepositoryImpl
import com.mashup.dorabangs.domain.repository.AIClassificationRepository
import com.mashup.dorabangs.domain.repository.FolderRepository
import com.mashup.dorabangs.domain.repository.PostsRepository
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
    abstract fun bindsUserRepository(
        repositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    @Reusable
    abstract fun bindsFolderRepository(
        repository: FolderRepositoryImpl,
    ): FolderRepository

    @Binds
    @Reusable
    abstract fun bindsDoraUrlCheckRepository(
        repository: DoraUrlCheckRepositoryImpl,
    ): DoraUrlCheckRepository

    @Binds
    @Reusable
    abstract fun bindsPostsRepository(
        repository: PostsRepositoryImpl,
    ): PostsRepository

    @Binds
    @Reusable
    abstract fun bindsAIClassificationRepository(
        repository: AIClassificationRepositoryImpl,
    ): AIClassificationRepository
}
